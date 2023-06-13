package com.example.yearproject.controller;

import com.example.yearproject.domain.User;
import com.example.yearproject.model.UserCreateForm;
import com.example.yearproject.model.UserDetailsDto;
import com.example.yearproject.repository.UserRepository;
import com.example.yearproject.service.FileUploadUtil;
import com.example.yearproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }




    @GetMapping
    public ResponseEntity<User> getUserById(@RequestParam("id") Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.isActive())
            {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("all")
    public ResponseEntity<Page<User>> getAllActiveUsers(Pageable pageable) {
        Page<User> activeUsers = userService.getAllActiveUsers(pageable);

//
        for (User user : activeUsers) {
            String baseDirectory = "C:\\Users\\Jane\\Downloads\\yearproject";
            String imagePath = baseDirectory + "\\photos\\" + user.getId() + "\\" + user.getImage();
            Path imageFilePath = Paths.get(imagePath);

            if (Files.exists(imageFilePath)) {
                user.setImage(imageFilePath.toString());
            }
        }


        return new ResponseEntity<>(activeUsers, HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/image")
    public ResponseEntity<Resource> getUserImage(@PathVariable Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String baseDirectory = "C:\\Users\\Jane\\Downloads\\yearproject";
        String imagePath = baseDirectory + "\\photos\\" + userId + "\\" + user.getImage();
        Path imageFilePath = Paths.get(imagePath);
        Resource resource = new UrlResource(imageFilePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("Image not found");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Modify the MediaType if needed
                .body(resource);
    }




    @PostMapping

    public ResponseEntity<User> createUser(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("dob") String dob,
                                           @RequestParam("email") String email,
                                           @RequestParam("bio") String bio,
                                           @RequestParam("yearGraduation") int yearGraduation,
                                           @RequestParam("faculty") String faculty,
                                           @RequestParam("image") MultipartFile image,

                                           @RequestParam(value = "active", defaultValue = "true") boolean active) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDob = LocalDate.parse(dob, formatter);
        String imageUrl = null;
        if (!image.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String uniqueFileName = generateUniqueFileName(originalFileName);

            String uploadDir = "photos";
            FileUploadUtil.saveFile(uploadDir, uniqueFileName, image);

            imageUrl = "/api/images/" + uniqueFileName;
        }
        UserCreateForm userCreateForm = new UserCreateForm(firstName, lastName, parsedDob, email, bio, yearGraduation, faculty,image,imageUrl);
        userCreateForm.setCreatedAt(LocalDateTime.now());
        userCreateForm.setUpdatedAt(LocalDateTime.now());
        userCreateForm.setActive(active); // Set the active status from the request
        User createdUser = userService.createUser(userCreateForm,image);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    private String generateUniqueFileName(String originalFileName) {
        String uniqueFileName = UUID.randomUUID().toString();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        return uniqueFileName + "." + fileExtension;

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) LocalDate dob,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) Integer yearGraduation,
            @RequestParam(required = false) String faculty,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String imgUrl
    ) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User existingUser = userOptional.get();

        if (firstName != null) {
            existingUser.setFirstName(firstName);
        }
        if (lastName != null) {
            existingUser.setLastName(lastName);
        }
        if (dob != null) {
            existingUser.setDob(dob);
        }
        if (bio != null) {
            existingUser.setBio(bio);
        }
        if (yearGraduation != null) {
            existingUser.setYearGraduation(yearGraduation.intValue());
        }
        if (faculty != null) {
            existingUser.setFaculty(faculty);
        }
        if (image != null && !image.isEmpty()) {
            try {
                // Save the image file
                String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
                String uniqueFileName = generateUniqueFileName(originalFileName);
                String uploadDir = "photos/" + existingUser.getId();
                FileUploadUtil.saveFile(uploadDir, uniqueFileName, image);

                // Update the user's image path or URL
                existingUser.setImage(uniqueFileName);
                existingUser.setImageUrl("/api/images/" + existingUser.getId() + "/" + uniqueFileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image file", e);
            }
        }
        if (imgUrl != null) {
            existingUser.setImageUrl(imgUrl);
        }

        existingUser.setUpdatedAt(LocalDateTime.now());

        // Update the user
        User updatedUser = userService.updateUser(existingUser);

        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping
    public ResponseEntity<Void> deactivateUser(@RequestParam("id") Long userId,
                                               @RequestParam("active") boolean active) {
        if (!active) {
            userService.deactivateUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/senders")
    public ResponseEntity<List<User>> getActiveSenders() {
        List<User> activeSenders = userService.getActiveSenders();
        return ResponseEntity.ok(activeSenders);
    }

    @GetMapping("/receivers")
    public ResponseEntity<List<User>> getActiveReceivers() {
        List<User> activeReceivers = userService.getActiveReceivers();
        return ResponseEntity.ok(activeReceivers);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
