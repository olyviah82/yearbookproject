package com.example.yearproject.service;

import com.example.yearproject.domain.User;

import com.example.yearproject.model.UserCreateForm;
import com.example.yearproject.model.UserUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

//    List<User> getAllUsers();
Page<User> getAllUsers(Pageable pageable);

    Optional<User> getUserById(Long userId);

//    User createUser(UserCreateForm userCreateForm,MultipartFile image) throws IOException;
    User createUser(UserCreateForm userCreateForm,MultipartFile image) throws IOException;

    User updateUser(User user);
    User updateUser(UserUpdateForm userUpdateForm,MultipartFile image) throws IOException;
    void deactivateUser(Long userId);

    boolean deleteUser(Long id);
    Page<User> getAllActiveUsers(Pageable pageable);

List<User>getActiveSenders();
List<User>getActiveReceivers();


}
