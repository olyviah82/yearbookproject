package com.example.yearproject.service;

import com.example.yearproject.domain.User;

import com.example.yearproject.model.UserCreateForm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

//    List<User> getAllUsers();
Page<User> getAllUsers(Pageable pageable);

    Optional<User> getUserById(Long userId);

    User createUser(UserCreateForm userCreateForm);

    User updateUser(User userCreateForm);
    void deactivateUser(Long userId);

    boolean deleteUser(Long id);
    Page<User> getAllActiveUsers(Pageable pageable);




}
