package com.gigglegazette.user_service.repository;

import com.gigglegazette.user_service.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile,String> {
}
