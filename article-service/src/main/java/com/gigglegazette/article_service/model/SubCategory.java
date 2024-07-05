package com.gigglegazette.article_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "subcategories")
public class SubCategory {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @NotBlank(message = "SubCategory name is required")
    @Size(min = 2, max = 100, message = "SubCategory name must be between 2 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description can be up to 255 characters")
    private String description;

    @NotNull(message = "Parent category is required")
    @DocumentReference(collection = "categories")
    private Category parentCategory;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructor

    public SubCategory(String name, String description, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull(message = "Parent category is required") Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(@NotNull(message = "Parent category is required") Category parentCategory) {
        this.parentCategory = parentCategory;
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

