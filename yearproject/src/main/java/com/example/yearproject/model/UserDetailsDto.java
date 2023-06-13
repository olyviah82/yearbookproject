package com.example.yearproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

public class UserDetailsDto {
    private Long id;
    private String firstName;
    private String LastName;
    private LocalDate dob;
    private String email;
    private String bio;
    private Integer yearGraduation;
    private String faculty;
    private BufferedImage image;

}
