package com.example.yearproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "first_name",nullable = false,length = 128)
    @Size(min = 3,max = 128)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private  String lastName;
    @Column(name = "dob",nullable = false)
    private LocalDate dob;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "bio",nullable = false)
    private String bio;
    @Column(name = "year_graduation",nullable = false)
    private int yearGraduation;
    @Column(name = "faculty",nullable = false)
    private String faculty;
//    @Column(name = "image",nullable = true,length = 64)
//    private String image="c;
    @Setter
    @Column(name ="is_active")
    private boolean active;
    @Column(name = "created_at",nullable = false)
    private  LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    public User(String firstName, String lastName, LocalDateTime dob, String email, String bio, int yearGraduation, String faculty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = LocalDate.from(dob);
        this.email = email;
        this.bio = bio;
        this.yearGraduation = yearGraduation;
        this.faculty = faculty;
//        this.image = image;
        this.active = true;
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    public boolean isActive() {
        return active;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getYearGraduation() {
        return yearGraduation;
    }

    public void setYearGraduation(int yearGraduation) {
        this.yearGraduation = yearGraduation;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }



    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
