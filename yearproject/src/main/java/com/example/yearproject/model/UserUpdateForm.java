package com.example.yearproject.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Getter
@Setter

public class UserUpdateForm {
    @NotNull(message = "the userid cannot be empty")
    private Long userId;
    private String firstName;
    private  String lastName;
    private LocalDate dob;
    private String bio;
    private Integer yearGraduation;
    private String faculty;
    private MultipartFile image;
}
