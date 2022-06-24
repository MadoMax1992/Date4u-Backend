package com.tutego.date4u.Commands;

import com.tutego.date4u.Photo;
import com.tutego.date4u.Service.PhotoService;
import com.tutego.date4u.Repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ShellComponent
public class PhotoCommands {

//    private PhotoService photoService;
    private final PhotoService photoService;
    private final PhotoRepository photoRepository;
    @Autowired
    public PhotoCommands(PhotoService photoService, PhotoRepository photoRepository) {
        this.photoService = photoService;
        this.photoRepository = photoRepository;
    }


//    @Autowired
//    public void setPhotoService( PhotoService photoService ) { this.photoService = photoService; }

//    @Autowired
//    public PhotoCommands (PhotoService photoService) {this.photoService =photoService;}
    @ShellMethod( "Show photo" )
    String showPhoto( String name ) {  // show-photo
        return photoService.download( name )
                .map( bytes -> {
                    try {
                        var image = ImageIO.read( new ByteArrayInputStream( bytes ) );
                        return "Width: " + image.getWidth() + ", Height: " + image.getHeight();
                    }
                    catch ( IOException e ) {
                        return "Unable to read image dimensions";
                    }
                } )
                .orElse( "Image not found" );
    }
    @ShellMethod("Upload photo")
    String uploadPhoto(String path) throws IOException {
        byte[] bytes = Files.readAllBytes( Paths.get( path ) );
        return "Uploaded " + photoService.upload( bytes );
    }

    @ShellMethod("save Photo")
    public void savePhoto(String path) {
        Photo photo = new Photo();
        photo.setName(path);
        photo.setProfile(18L);
        photoRepository.save(photo);
    }

}
