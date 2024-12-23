package com.example.todo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
import java.nio.ByteBuffer;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Let Hibernate generate UUID
    @Column(columnDefinition = "BINARY(16)") // Store UUID as BINARY(16) in the DB
    private UUID id; // Use UUID type for the ID

    private String title;
    private String description;
    private boolean completed;

    @CreationTimestamp
    private LocalDateTime createdDate;

    // Constructor with parameters (required for your use case)
    public Todo(UUID id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // No-args constructor (required by JPA/Hibernate)
    public Todo() {
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    // Helper method to generate UUID byte array from UUID (Optional for byte[]
    // persistence)
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID(); // Generate a new UUID if not already set
        }
    }

    // Helper method to convert UUID to byte array
    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }

    // Helper method to convert byte array to UUID
    public static UUID bytesToUUID(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        long mostSigBits = buffer.getLong();
        long leastSigBits = buffer.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }
}