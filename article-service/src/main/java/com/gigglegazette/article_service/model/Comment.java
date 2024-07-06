package com.gigglegazette.article_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "comments")
public class Comment {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @NotNull(message = "Article is required")
    @DocumentReference(collection = "articles")
    private Article article;

    @NotBlank(message = "Author ID is required")
    private String authorId;

    @NotBlank(message = "Content is required")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Constructor

    public Comment(String id, Article article, String authorId, String content
            , LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.article = article;
        this.authorId = authorId;
        this.content = content;
    }

    // Getters and Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotNull(message = "Article ID is required") Article getArticle() {
        return article;
    }

    public void setArticle(@NotNull(message = "Article ID is required") Article article) {
        this.article = article;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

