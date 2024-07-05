package com.gigglegazette.article_service.repository;

import com.gigglegazette.article_service.model.Comment;
import com.gigglegazette.article_service.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByArticle_Id(String articleId);

}
