package com.example.yearproject.controller;

import com.example.yearproject.domain.User;
import com.example.yearproject.model.UserCreateForm;
import com.example.yearproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        Page<User> users = userService.getAllActiveUsers(pageable);

        return new ResponseEntity<>(users, HttpStatus.OK);
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
        UserCreateForm userCreateForm = new UserCreateForm(firstName, lastName, parsedDob, email, bio, yearGraduation, faculty,image);
        userCreateForm.setCreatedAt(LocalDateTime.now());
        userCreateForm.setUpdatedAt(LocalDateTime.now());
        userCreateForm.setActive(active); // Set the active status from the request
        User createdUser = userService.createUser(userCreateForm,image);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @PutMapping
    public ResponseEntity<User>updateTodo(@RequestParam Long id,
                                          @RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName,
                                          @RequestParam(required = false) LocalDate dob,
                                          @RequestParam(required = false) String bio,
                                          @RequestParam(required = false) Integer yearGraduation,
                                          @RequestParam(required = false) String faculty,
                                          @RequestParam(required = false) MultipartFile image
                                          ){
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User existingUser = userOptional.get();


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
