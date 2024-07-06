package com.gigglegazette.article_service.repository;

import com.gigglegazette.article_service.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends MongoRepository<Article, String> {
    Optional<List<Article>> findByAuthorId(String authorId);
}
