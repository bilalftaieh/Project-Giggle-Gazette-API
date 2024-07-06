package com.gigglegazette.user_service.repository;

import com.gigglegazette.user_service.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends MongoRepository<Permission,String> {
    List<Permission> findByAllowedRolesId(String roleId);

}
