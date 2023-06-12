package com.example.yearproject.service;


import com.example.yearproject.domain.Note;
import com.example.yearproject.domain.User;
import com.example.yearproject.model.NoteCreateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface NoteService {

    List<Note> getAllNotesByRecipient(User recipient);
    List<Note>getAllNotesBySender(User sender);


    Optional<Note> getNoteById(Long noteId);
    Note createNote(NoteCreateForm noteCreateForm);
    Page<Note> getAllActiveNotes(Pageable pageable);

    void deleteNoteById(Note note);



}
