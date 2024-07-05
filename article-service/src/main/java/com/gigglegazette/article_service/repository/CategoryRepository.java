package com.gigglegazette.article_service.repository;

import com.gigglegazette.article_service.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category,String> {
}
