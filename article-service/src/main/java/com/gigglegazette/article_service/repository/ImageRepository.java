package com.gigglegazette.article_service.repository;

import com.gigglegazette.article_service.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image,String> {
    List<Image> findByArticle_Id(String articleId);

}
