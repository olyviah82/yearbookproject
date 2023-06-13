package com.example.yearproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 128)
    @Size(min = 3, max = 128)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "dob", nullable = false)
    private LocalDate dob;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "bio", nullable = false)
    private String bio;
    @Column(name = "year_graduation", nullable = false)
    private int yearGraduation;
    @Column(name = "faculty", nullable = false)
    private String faculty;

    @Column(name = "image",nullable = true)

    private String image;
@Column(name = "image_url")
private String imageUrl;
    @Setter
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public User(String firstName, String lastName, LocalDateTime dob, String email, String bio, int yearGraduation, String faculty,String image,String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = LocalDate.from(dob);
        this.email = email;
        this.bio = bio;
        this.yearGraduation = yearGraduation;
        this.faculty = faculty;
       this.image = image;
       this.imageUrl=imageUrl;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }




}