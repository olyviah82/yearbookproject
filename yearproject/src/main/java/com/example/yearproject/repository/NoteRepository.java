package com.example.yearproject.repository;

import com.example.yearproject.domain.Note;
import com.example.yearproject.domain.User;
import com.example.yearproject.model.NoteCreateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Long> {
//    List<Note> findBySenderAndActiveTrue(User sender);
//    List<Note> findBySenderIdOrReceiverIdAndActiveTrue(User sender, User receiver);
//
//    List<Note> findBySenderOrReceiverAndActiveTrue(User user, User user1);
//
//    Page<Note> findByReceiverIdAndActiveTrue(User user, Pageable pageable);
Page<Note> findAll(Pageable pageable);
    Page<Note> findByActiveTrue(Pageable pageable);

    List<Note> findAllByReceiverId(User recipient);
}
