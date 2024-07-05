package com.gigglegazette.user_service.repository;

import com.gigglegazette.user_service.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role,String> {
}
