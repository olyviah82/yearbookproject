package com.example.yearproject.repository;

import com.example.yearproject.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByActiveTrue(Pageable pageable);
    List<User> findByActive(boolean active);

    Optional<User> findByEmail(String email);



}
