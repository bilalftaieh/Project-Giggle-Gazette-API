package com.gigglegazette.user_service.model;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "roles")
public class Role {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @NotBlank(message = "Role name is required")
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Role(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank(message = "Role name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Role name is required") String name) {
        this.name = name;
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

    // Constructors, getters, setters, and other methods
}

