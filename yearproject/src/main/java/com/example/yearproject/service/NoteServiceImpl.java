package com.example.yearproject.service;

import com.example.yearproject.domain.Note;
import com.example.yearproject.domain.User;
import com.example.yearproject.model.NoteCreateForm;
import com.example.yearproject.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotesByRecipient(User recipient) {
        return noteRepository.findAllByReceiverId(recipient);
    }

    @Override
    public List<Note> getAllNotesBySender(User sender) {
        return noteRepository.findAllBySenderId(sender);
    }

    @Override

    public Optional<Note> getNoteById(Long noteId) {
        return noteRepository.findById(noteId);
    }

    @Override
    public Note createNote(NoteCreateForm noteCreateForm) {
        Note note = new Note();
        note.setContent(noteCreateForm.getContent());
        note.setReceiverId(noteCreateForm.getReceiverId());
        note.setSenderId(noteCreateForm.getSenderId());
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setActive(noteCreateForm.isActive());
        note.setCreatedAt(LocalDateTime.now());
        return noteRepository.save(note);

    }

    @Override
    public Page<Note> getAllActiveNotes(Pageable pageable) {
        return noteRepository.findByActiveTrue(pageable);
    }

    @Override
    public void deleteNoteById(Note note) {
        noteRepository.delete(note);
    }




}
