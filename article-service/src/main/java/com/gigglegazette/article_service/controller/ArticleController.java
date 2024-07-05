package com.gigglegazette.article_service.controller;

import com.gigglegazette.article_service.client.UserClient;
import com.gigglegazette.article_service.model.Article;
import com.gigglegazette.article_service.repository.ArticleRepository;
import com.gigglegazette.article_service.util.ArticleResponse;
import com.gigglegazette.article_service.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserClient userClient;

    /**
     * Create a new article.
     *
     * @param article The details of the article to be created.
     * @return A response entity with a success message if the article is created successfully.
     */
    @PostMapping
    public ResponseEntity<CustomResponse<ArticleResponse>> createArticle(@RequestBody Article article) {
        try {
            article.setCreatedAt(LocalDateTime.now());
            article.setUpdatedAt(LocalDateTime.now());
            Article savedArticle = articleRepository.save(article);
            Object author = userClient.getUserById(savedArticle.getAuthorId()).getBody(); // Fetch author details
            ArticleResponse articleResponse = new ArticleResponse(savedArticle, author);
            return new ResponseEntity<>(new CustomResponse<>("Article created successfully.", articleResponse, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error creating article: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch all articles.
     *
     * @return A response entity with the list of all articles and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<ArticleResponse>>> getAllArticles() {
        try {
            List<Article> articles = articleRepository.findAll();
            List<ArticleResponse> articleResponses = new ArrayList<>();

            for (Article article : articles) {
                Object author = userClient.getUserById(article.getAuthorId()).getBody(); // Fetch author details
                ArticleResponse articleResponse = new ArticleResponse(article, author);
                articleResponses.add(articleResponse);
            }

            return new ResponseEntity<>(new CustomResponse<>("Articles retrieved successfully.", articleResponses, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving articles: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch an article by its ID.
     *
     * @param id The ID of the article to be fetched.
     * @return A response entity with the article details if found, otherwise a not found message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<ArticleResponse>> getArticleById(@PathVariable String id) {
        try {
            Optional<Article> articleOptional = articleRepository.findById(id);
            if (articleOptional.isPresent()) {
                Article article = articleOptional.get();
                Object author = userClient.getUserById(article.getAuthorId()).getBody(); // Fetch author details
                ArticleResponse articleResponse = new ArticleResponse(article, author);
                return new ResponseEntity<>(new CustomResponse<>("Article retrieved successfully.", articleResponse, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Article not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving article: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing article's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id The ID of the article to be updated.
     * @param articleDetails The updated article details.
     * @return A response entity indicating whether the update was successful or the article was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<ArticleResponse>> updateArticle(@PathVariable String id, @RequestBody Article articleDetails) {
        try {
            Optional<Article> articleOptional = articleRepository.findById(id);
            if (articleOptional.isPresent()) {
                Article article = articleOptional.get();
                if (articleDetails.getTitle() != null) article.setTitle(articleDetails.getTitle());
                if (articleDetails.getContent() != null) article.setContent(articleDetails.getContent());
                if (articleDetails.getAuthorId() != null) article.setAuthorId(articleDetails.getAuthorId());
                if (articleDetails.getTags() != null) article.setTags(articleDetails.getTags());
                if (articleDetails.getStatus() != null) article.setStatus(articleDetails.getStatus());
                if (articleDetails.getLikes() >= 0) article.setLikes(articleDetails.getLikes());
                article.setUpdatedAt(LocalDateTime.now());
                Article updatedArticle = articleRepository.save(article);
                Object author = userClient.getUserById(updatedArticle.getAuthorId()).getBody(); // Fetch author details
                ArticleResponse articleResponse = new ArticleResponse(updatedArticle, author);
                return new ResponseEntity<>(new CustomResponse<>("Article updated successfully.", articleResponse, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Article not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error updating article: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete an article by its ID.
     *
     * @param id The ID of the article to be deleted.
     * @return A response entity indicating whether the deletion was successful or the article was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteArticle(@PathVariable String id) {
        try {
            Optional<Article> articleOptional = articleRepository.findById(id);
            if (articleOptional.isPresent()) {
                articleRepository.deleteById(id);
                return new ResponseEntity<>(new CustomResponse<>("Article deleted successfully.", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Article not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error deleting article: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
