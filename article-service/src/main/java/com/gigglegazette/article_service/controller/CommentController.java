package com.gigglegazette.article_service.controller;

import com.gigglegazette.article_service.client.UserClient;
import com.gigglegazette.article_service.model.Comment;
import com.gigglegazette.article_service.repository.CommentRepository;
import com.gigglegazette.article_service.util.CommentResponse;
import com.gigglegazette.article_service.util.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserClient userClient;

    /**
     * Create a new comment.
     *
     * @param comment The details of the comment to be created.
     * @return A response entity with a success message if the comment is created successfully.
     */
    @PostMapping
    public ResponseEntity<?>
    createComment(@Valid @RequestBody Comment comment, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            List<Map<String, String>> errorDetails = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                Map<String, String> errorDetail = new HashMap<>();
                errorDetail.put("field", error.getField());
                errorDetail.put("message", error.getDefaultMessage());
                errorDetails.add(errorDetail);
            }
            return ResponseEntity.status(400).body(
                    new CustomResponse<>("Validation Failed", errorDetails, false)
            );
        }
        try {
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());
            Comment savedComment = commentRepository.save(comment);
            Object author = userClient.getUserById(savedComment.getAuthorId()).getBody(); // Fetch author details
            CommentResponse commentResponse = new CommentResponse(savedComment, author);
            return new ResponseEntity<>(new CustomResponse<>("Comment created successfully.", commentResponse, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error creating comment: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch all comments.
     *
     * @return A response entity with the list of all comments and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<CommentResponse>>> getAllComments() {
        try {
            List<Comment> comments = commentRepository.findAll();
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                Object author = userClient.getUserById(comment.getAuthorId()).getBody(); // Fetch author details
                CommentResponse commentResponse = new CommentResponse(comment, author);
                commentResponses.add(commentResponse);
            }

            return new ResponseEntity<>(new CustomResponse<>("Comments retrieved successfully.", commentResponses, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving comments: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch a comment by its ID.
     *
     * @param id The ID of the comment to be fetched.
     * @return A response entity with the comment details if found, otherwise a not found message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<CommentResponse>> getCommentById(@PathVariable String id) {
        try {
            Optional<Comment> commentOptional = commentRepository.findById(id);
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                Object author = userClient.getUserById(comment.getAuthorId()).getBody(); // Fetch author details
                CommentResponse commentResponse = new CommentResponse(comment, author);
                return new ResponseEntity<>(new CustomResponse<>("Comment retrieved successfully.", commentResponse, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Comment not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving comment: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing comment's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id             The ID of the comment to be updated.
     * @param commentDetails The updated comment details.
     * @return A response entity indicating whether the update was successful or the comment was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<CommentResponse>> updateComment(@PathVariable String id, @RequestBody Comment commentDetails) {
        try {
            Optional<Comment> commentOptional = commentRepository.findById(id);
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                if (commentDetails.getArticle() != null) comment.setArticle(commentDetails.getArticle());
                if (commentDetails.getAuthorId() != null) comment.setAuthorId(commentDetails.getAuthorId());
                if (commentDetails.getContent() != null) comment.setContent(commentDetails.getContent());
                Comment updatedComment = commentRepository.save(comment);
                Object author = userClient.getUserById(updatedComment.getAuthorId()).getBody(); // Fetch author details
                CommentResponse commentResponse = new CommentResponse(updatedComment, author);
                return new ResponseEntity<>(new CustomResponse<>("Comment updated successfully.", commentResponse, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Comment not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error updating comment: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a comment by its ID.
     *
     * @param id The ID of the comment to be deleted.
     * @return A response entity indicating whether the deletion was successful or the comment was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteComment(@PathVariable String id) {
        try {
            Optional<Comment> commentOptional = commentRepository.findById(id);
            if (commentOptional.isPresent()) {
                commentRepository.deleteById(id);
                return new ResponseEntity<>(new CustomResponse<>("Comment deleted successfully.", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>("Comment not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error deleting comment: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch all comments for a specific article.
     *
     * @param articleId The ID of the article for which comments are to be retrieved.
     * @return A response entity with the list of comments for the specified article.
     */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<CustomResponse<List<CommentResponse>>> getCommentsByArticleId(@PathVariable String articleId) {
        try {
            List<Comment> comments = commentRepository.findByArticle_Id(articleId);
            List<CommentResponse> commentResponses = new ArrayList<>();

            for (Comment comment : comments) {
                Object author = userClient.getUserById(comment.getAuthorId()).getBody(); // Fetch author details
                CommentResponse commentResponse = new CommentResponse(comment, author);
                commentResponses.add(commentResponse);
            }

            return new ResponseEntity<>(new CustomResponse<>("Comments for the article retrieved successfully.", commentResponses, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving comments for article: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Fetch Comments by their Author ID.
     *
     * @param authorId The ID of the Author whose comments are to be fetched.
     * @return A response entity with the article details if found, otherwise a not found message.
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<CustomResponse<List<CommentResponse>>> getArticlesByAuthorId(@PathVariable String authorId) {
        try {
            Optional<List<Comment>> commentsOptional =
                    commentRepository.findByAuthorId(authorId);
            if (commentsOptional.isPresent()) {
                List<Comment> comments = commentsOptional.get();
                Object author = userClient.getUserById(authorId).getBody(); // Fetch author details once
                List<CommentResponse> commentResponses = new ArrayList<>();
                for (Comment comment : comments) {
                    CommentResponse commentResponse =
                            new CommentResponse(comment, author);
                    commentResponses.add(commentResponse);
                }
                return new ResponseEntity<>(
                        new CustomResponse<>
                                ("Comments retrieved successfully.",
                                        commentResponses, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new CustomResponse<>("Comments not found.", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Error retrieving comments: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
