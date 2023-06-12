package com.example.yearproject.service;

import com.example.yearproject.domain.User;
import com.example.yearproject.model.UserCreateForm;
import com.example.yearproject.model.UserUpdateForm;
import com.example.yearproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
        if (id == null) {
            return Optional.empty();
        }
        return userRepository.findById(id);
    }

    @Override
    public User createUser(UserCreateForm userCreateForm,MultipartFile image) throws IOException {
        if (userRepository.findByEmail(userCreateForm.getEmail()).isPresent()) {
            throw new RuntimeException("user already exist ");
        }
        User user = User.builder()
                .firstName(userCreateForm.getFirstName())
                .lastName(userCreateForm.getLastName())
                .dob(userCreateForm.getDob())
                .email(userCreateForm.getEmail())
                .bio(userCreateForm.getBio())
                .yearGraduation(userCreateForm.getYearGraduation())
                .faculty(userCreateForm.getFaculty())
                .createdAt(userCreateForm.getCreatedAt())
                .updatedAt(userCreateForm.getUpdatedAt())
                .active(true)
                .build();
        // Save the user entity to the database
        if (!image.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String uniqueFileName = generateUniqueFileName(originalFileName);

            user.setImage(uniqueFileName);
            User savedUser = userRepository.save(user);

            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, uniqueFileName, image);
        } else {
            // Save the user entity to the database without an image
            userRepository.save(user);
        }

        return user;
    }

    private String generateUniqueFileName(String originalFileName) {

        String uniqueFileName = UUID.randomUUID().toString();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        return uniqueFileName + "." + fileExtension;

    }


    @Override
    public User updateUser(User user) {

        return userRepository.save(user);

    }

    @Override
    public User updateUser(UserUpdateForm userUpdateForm,MultipartFile image) throws IOException {
        Optional<User> userOptional = userRepository.findById(userUpdateForm.getUserId());
        if (userOptional.isEmpty()) throw new RuntimeException("user not found");


        User existingUser = userOptional.get();
        if (!existingUser.isActive()) throw new RuntimeException("user is deactivated contact your administrator");


        // Update the fields if provided
        if (userUpdateForm.getFirstName() != null) {
            existingUser.setFirstName(userUpdateForm.getFirstName());
        }
        if (userUpdateForm.getLastName() != null) {
            existingUser.setLastName(userUpdateForm.getLastName());
        }
        if (userUpdateForm.getDob() != null) {
            existingUser.setDob(userUpdateForm.getDob());
        }
        if (userUpdateForm.getBio() != null) {
            existingUser.setBio(userUpdateForm.getBio());
        }
        if (userUpdateForm.getYearGraduation() != null) {
            existingUser.setYearGraduation(userUpdateForm.getYearGraduation());
        }
        if (userUpdateForm.getFaculty()!= null) {
            existingUser.setFaculty(userUpdateForm.getFaculty() );
        }
        if (!image.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String uniqueFileName = generateUniqueFileName(originalFileName);

            existingUser.setImage(uniqueFileName);
            User savedUser = userRepository.save(existingUser);

            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDir, uniqueFileName, image);
        }
       return updateUser(existingUser);
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

    public List<User> getActiveSenders() {
        return userRepository.findByActive(true);
    }

    public List<User> getActiveReceivers() {
        return userRepository.findByActive(true);
    }

    @Override
    public Page<User> getAllActiveUsers(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable);
    }


}
