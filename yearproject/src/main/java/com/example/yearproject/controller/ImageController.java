package com.example.yearproject.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/{userId}/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable Long userId, @PathVariable String fileName) throws IOException {
        String imagePath = uploadDir + "/photos/" + userId + "/" + fileName;
        Path imageFilePath = Paths.get(imagePath);
        Resource resource = new UrlResource(imageFilePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("Image not found");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}
