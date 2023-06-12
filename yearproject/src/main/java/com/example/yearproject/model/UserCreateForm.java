package com.example.yearproject.model;

import jakarta.validation.constraints.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;



    public class UserCreateForm {

        @NotBlank(message = "First name is required")
        @Size(min = 3, max = 128, message = "First name must be between 3 and 128 characters")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotNull(message = "Date of birth is required")
        private LocalDate dob;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(min = 3, max = 128, message = "Email must be between 3 and 128 characters")
        private String email;

        @NotBlank(message = "Bio is required")
        @Size(min = 10, max = 1000, message = "Bio must be between 10 and 1000 characters")
        private String bio;

        @NotNull(message = "Year graduation is required")
        @Min(value = 1970, message = "Year graduation must be greater than or equal to 1900")
        private int yearGraduation;

        @NotNull(message = "Faculty is required")
        private String faculty;
        private MultipartFile image;

//        private byte[] image;
        private boolean active;
        //constructors
        private  LocalDateTime createdAt;
        private  LocalDateTime updatedAt;
        public UserCreateForm(String firstName, String lastName, LocalDate dob, String email, String bio, int yearGraduation,
                              String faculty,MultipartFile image) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.dob = dob;
            this.email = email;
            this.bio = bio;
            this.yearGraduation = yearGraduation;
            this.faculty = faculty;
           this.image=image;
             this.createdAt=LocalDateTime.now();
            this.updatedAt=LocalDateTime.now();
            this.active=true;

        }
        public UserCreateForm() {
            // Default constructor
        }

        public MultipartFile getImage() {
            return image;
        }

        public void setImage(MultipartFile image) {
            this.image = image;
        }

        //getters and setters
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

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
