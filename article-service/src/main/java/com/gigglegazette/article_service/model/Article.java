package com.gigglegazette.article_service.model;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "articles")
public class Article {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, message = "Content must be at least 10 characters")
    private String content;

    @NotBlank(message = "Author ID is required")
    private String authorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Tags cannot be null")
    @Size(min = 1, message = "At least one tag is required")
    private List<String> tags;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(DRAFT|PUBLISHED|ARCHIVED)$", message = "Status must be one of the following: DRAFT, PUBLISHED, ARCHIVED")
    private String status;

    @Min(value = 0, message = "Likes cannot be negative")
    private int likes;

    // Constructor

    public Article(String title, String content, String authorId,
                   List<String> tags, String status, int likes) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.tags = tags;
        this.status = status;
        this.likes = likes;
    }


    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

}
