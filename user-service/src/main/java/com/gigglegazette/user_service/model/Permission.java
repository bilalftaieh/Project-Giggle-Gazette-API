package com.gigglegazette.user_service.model;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Set;

public class Permission {
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @NotBlank(message = "Permission name is required")
    private String name;

    @NotNull(message = "Allowed roles are required")
    @DocumentReference(collection = "roles")
    private Set<Role> allowedRoles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Permission(String name, Set<Role> allowedRoles) {
        this.name = name;
        this.allowedRoles = allowedRoles;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank(message = "Permission name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Permission name is required") String name) {
        this.name = name;
    }

    public @NotNull(message = "Allowed roles are required") Set<Role> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(@NotNull(message = "Allowed roles are required") Set<Role> allowedRoles) {
        this.allowedRoles = allowedRoles;
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
