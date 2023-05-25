package com.example.yearproject.model;

import com.example.yearproject.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class NoteCreateForm {
    @NotEmpty(message = "Content cannot be empty")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;
    @NotNull(message = "Receiver ID cannot be null")
    private User receiverId;
    @NotNull(message = "Sender ID cannot be null")
    private User senderId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public NoteCreateForm() {

    }

    public NoteCreateForm(String content, User receiverId, User senderId) {
        this.content = content;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
        this.active=true;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public User getSenderId() {
        return senderId;
    }

    public void setSenderId(User senderId) {
        this.senderId = senderId;
    }

    public User getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(User receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
