package com.tutego.date4u.Service;

import com.tutego.date4u.AwtBicubicThumbnail;
import com.tutego.date4u.FileSystem;
import com.tutego.date4u.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Validated
public class PhotoService {
    private final FileSystem fs;
    private final AwtBicubicThumbnail abt;

    @Autowired
    public PhotoService(FileSystem fs, AwtBicubicThumbnail abt) {
        this.fs = fs;
        this.abt = abt;
    }

    public Optional<byte[]> download(String imageName ) {
        try { return Optional.of( fs.load( imageName + ".jpg" ) ); }
        catch ( UncheckedIOException e ) { return Optional.empty(); }
    }


    public String upload( byte[] imageBytes ) {
        String imageName = UUID.randomUUID().toString();

        // First: store original image
        fs.store( imageName + ".jpg", imageBytes );

        // Second: store thumbnail
        byte[] thumbnailBytes = abt.thumbnail( imageBytes );
        fs.store( imageName + "-thumb.jpg", thumbnailBytes );

        return imageName;
    }

    public Optional<byte[]> download(@Valid Photo photo ) {
        return download(photo.name);
    }
}
