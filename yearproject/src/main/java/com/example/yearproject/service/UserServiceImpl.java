package com.example.yearproject.service;

import com.example.yearproject.domain.User;
import com.example.yearproject.model.UserCreateForm;
import com.example.yearproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(UserCreateForm userCreateForm) {
        // Map the fields from UserCreateForm to User entity
        User user = new User();
        user.setFirstName(userCreateForm.getFirstName());
        user.setLastName(userCreateForm.getLastName());
        user.setDob(userCreateForm.getDob());
        user.setEmail(userCreateForm.getEmail());
        user.setBio(userCreateForm.getBio());
        user.setYearGraduation(userCreateForm.getYearGraduation());
        user.setFaculty(userCreateForm.getFaculty());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(userCreateForm.isActive());
        // Save the user entity to the database
        return userRepository.save(user);
    }




    @Override
    public User updateUser(User userCreateForm) {
        Optional<User> userOptional = userRepository.findById(userCreateForm.getId());
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            if (existingUser.isActive()) {
                existingUser.setFirstName(userCreateForm.getFirstName());
                existingUser.setLastName(userCreateForm.getLastName());
                existingUser.setDob(userCreateForm.getDob());
                existingUser.setEmail(userCreateForm.getEmail());
                existingUser.setBio(userCreateForm.getBio());
                existingUser.setYearGraduation(userCreateForm.getYearGraduation());
                existingUser.setFaculty(userCreateForm.getFaculty());
                existingUser.setUpdatedAt(LocalDateTime.now());
                return userRepository.save(existingUser);
            } else {
                // User is not active, return null or handle accordingly
                return null;
            }
        } else {
            // User not found, return null or handle accordingly
            return null;
        }
    }

    @Override
    public void deactivateUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            user.setActive(false);
            userRepository.save(user);
        });
    }


    @Override
    public boolean deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<User> getAllActiveUsers(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable);
    }


}
