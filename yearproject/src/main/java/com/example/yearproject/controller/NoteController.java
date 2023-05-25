package com.example.yearproject.controller;

import com.example.yearproject.domain.Note;
import com.example.yearproject.domain.User;
import com.example.yearproject.model.NoteCreateForm;
import com.example.yearproject.service.NoteService;
import com.example.yearproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService=userService;
    }

@PostMapping
    public ResponseEntity<Note> createNote(@RequestParam("content") String content,
                                           @RequestParam("senderid") User senderId,
                                           @RequestParam("receiverid") User receiverId) {
    Optional<User> senderOptional = userService.getUserById(senderId.getId());
    Optional<User> receiverOptional = userService.getUserById(receiverId.getId());

    if (senderOptional.isPresent() && receiverOptional.isPresent()) {
        User sender = senderOptional.get();
        User receiver = receiverOptional.get();

        NoteCreateForm noteCreateForm = new NoteCreateForm();
        noteCreateForm.setContent(content);
        noteCreateForm.setSenderId(sender);
        noteCreateForm.setReceiverId(receiver);
        noteCreateForm.setCreatedAt(LocalDateTime.now());
        noteCreateForm.setUpdatedAt(LocalDateTime.now());
        noteCreateForm.setActive(true);

        Note createdNote = noteService.createNote(noteCreateForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
                                           }

    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable("noteId") Long noteId) {
        Optional<Note> noteOptional = noteService.getNoteById(noteId);

        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            return ResponseEntity.ok(note);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("all")
    public ResponseEntity<Page<Note>> getAllActiveNotes(Pageable pageable) {
        Page<Note> notes= noteService.getAllActiveNotes(pageable);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    @GetMapping("/receiver/{recipientId}")
    public ResponseEntity<List<Note>> getAllNotesByRecipient(@PathVariable("recipientId") Long recipientId) {
        // Retrieve the recipient user from the repository or service
        User recipient = userService.getUserById(recipientId).orElse(null);

        if (recipient != null) {
            List<Note> notes = noteService.getAllNotesByRecipient(recipient);
            return ResponseEntity.ok(notes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
