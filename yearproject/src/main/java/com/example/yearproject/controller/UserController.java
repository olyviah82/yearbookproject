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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

                                           @RequestParam(value = "active", defaultValue = "true") boolean active) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Dob ;; "+dob );
        LocalDate parsedDob = LocalDate.parse(dob, formatter);
        UserCreateForm userCreateForm = new UserCreateForm(firstName, lastName, parsedDob, email, bio, yearGraduation, faculty);
        userCreateForm.setCreatedAt(LocalDateTime.now());
        userCreateForm.setUpdatedAt(LocalDateTime.now());
        userCreateForm.setActive(active); // Set the active status from the request
        User createdUser = userService.createUser(userCreateForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @PutMapping
    public ResponseEntity<User>updateTodo(@RequestParam Long id,
                                          @RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName,
                                          @RequestParam(required = false) LocalDate dob,
                                          @RequestParam(required = false) String bio,
                                          @RequestParam(required = false) Integer yearGraduation,
                                          @RequestParam(required = false) String faculty
                                          ){
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User existingUser = userOptional.get();

        // Update the fields if provided
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
            existingUser.setYearGraduation(yearGraduation);
        }
        if (faculty != null) {
            existingUser.setFaculty(faculty);
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
