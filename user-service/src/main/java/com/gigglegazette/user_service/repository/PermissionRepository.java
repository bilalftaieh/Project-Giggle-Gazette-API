package com.gigglegazette.user_service.repository;

import com.gigglegazette.user_service.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission,String> {
}
