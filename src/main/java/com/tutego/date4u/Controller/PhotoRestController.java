package com.tutego.date4u.Controller;

import com.tutego.date4u.Service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class PhotoRestController {


    private final PhotoService photoService;

    @Autowired
    public PhotoRestController(PhotoService photoService) {
        this.photoService = photoService;
    }



    @GetMapping( path     = "/api/photo/random",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] randomPhoto() throws IOException {

        InputStream as = new ByteArrayInputStream(
                photoService.download("98").orElse(null));

        //aufgabe lade photo von DB
        Resource resource = new UrlResource(
                "https://getwallpapers.com/wallpaper/full/1/1/7/264012.jpg"
        );
        InputStream inputStream = resource.getInputStream();
        return StreamUtils.copyToByteArray( as );
    }
}