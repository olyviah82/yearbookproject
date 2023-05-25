package com.example.yearproject.repository;

import com.example.yearproject.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
     Page<User> findAll(Pageable pageable);
    Page<User> findByActiveTrue(Pageable pageable);
}
