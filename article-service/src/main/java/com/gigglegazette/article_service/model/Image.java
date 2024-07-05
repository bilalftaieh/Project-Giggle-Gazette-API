package com.gigglegazette.article_service.model;

import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "images")
public class Image {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @DocumentReference(collection = "articles")
    private Article article;

    @NotBlank(message = "URL is required")
    private String url;

    @NotBlank(message = "Image name is required")
    @Size(min = 1, max = 100,
            message = "Image name must be between 1 and 100 characters")
    private String name;

    private String description;

    @NotBlank(message = "Content type is required")
    @Pattern(regexp = "^image/(png|jpg|jpeg|gif|bmp|webp)$", message = "Content type must be one of the following: image/png, image/jpg, image/jpeg, image/gif, image/bmp, image/webp")
    private String contentType;

    @Min(value = 0, message = "Size must be a positive number")
    private long size; // Size in bytes

    @Min(value = 1, message = "Width must be a positive number")
    private int width;

    @Min(value = 1, message = "Height must be a positive number")
    private int height;

    private LocalDateTime uploadedAt;

    private LocalDateTime lastAccessedAt;

    @NotNull(message = "Is Public must be specified")
    private boolean isPublic;

    // Constructor

    public Image(String url, String name, Article article, String description,
                 String contentType, long size
            , int width, int height, boolean isPublic) {
        this.url = url;
        this.article = article;
        this.name = name;
        this.description = description;
        this.contentType = contentType;
        this.size = size;
        this.width = width;
        this.height = height;
        this.uploadedAt = LocalDateTime.now();
        this.lastAccessedAt = LocalDateTime.now();
        this.isPublic = isPublic;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public LocalDateTime getLastAccessedAt() {
        return lastAccessedAt;
    }

    public void setLastAccessedAt(LocalDateTime lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}

