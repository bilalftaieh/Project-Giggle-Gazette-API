package com.gigglegazette.article_service.repository;

import com.gigglegazette.article_service.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
