package com.gigglegazette.article_service.repository;

import com.gigglegazette.article_service.model.SubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubCategoryRepository extends MongoRepository<SubCategory,String> {
}
