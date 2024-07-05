package com.gigglegazette.user_service.controller;

import com.gigglegazette.user_service.model.Permission;
import com.gigglegazette.user_service.repository.PermissionRepository;
import com.gigglegazette.user_service.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * Retrieve all permissions from the database.
     *
     * @return A response entity containing a list of all permissions and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<Permission>>> getAllPermissions() {
        try {
            List<Permission> permissions = permissionRepository.findAll();
            return ResponseEntity.ok(
                    new CustomResponse<>("Permissions retrieved successfully", permissions, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving permissions: " + e.getMessage(), null, false));
        }
    }

    /**
     * Fetch a specific permission by its unique ID.
     *
     * @param id The ID of the permission to be fetched.
     * @return A response entity containing the permission if found, or a 'not found' message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Permission>> getPermissionById(@PathVariable String id) {
        try {
            Optional<Permission> permission = permissionRepository.findById(id);
            return permission.map(value -> ResponseEntity.ok(
                            new CustomResponse<>("Permission retrieved successfully", value, true)))
                    .orElseGet(() -> ResponseEntity.status(404).body(
                            new CustomResponse<>("Permission not found", null, false)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving the permission: " + e.getMessage(), null, false));
        }
    }

    /**
     * Create a new permission with the details provided in the request body.
     *
     * @param permission The permission details to be saved.
     * @return A response entity indicating that the permission was created successfully.
     */
    @PostMapping
    public ResponseEntity<CustomResponse<String>> createPermission(@RequestBody Permission permission) {
        try {
            permission.setCreatedAt(LocalDateTime.now());
            permission.setUpdatedAt(LocalDateTime.now());
            permissionRepository.save(permission);
            return ResponseEntity.status(201).body(
                    new CustomResponse<>("Permission created successfully", null, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while creating the permission: " + e.getMessage(), null, false));
        }
    }

    /**
     * Update an existing permission's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id The ID of the permission to be updated.
     * @param permission The updated permission details.
     * @return A response entity indicating whether the update was successful or the permission was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> updatePermission(@PathVariable String id, @RequestBody Permission permission) {
        try {
            Optional<Permission> existingPermissionOptional = permissionRepository.findById(id);
            if (existingPermissionOptional.isPresent()) {
                Permission existingPermission = existingPermissionOptional.get();

                // Update fields from the request body
                if (permission.getName() != null) existingPermission.setName(permission.getName());
                if (permission.getAllowedRoles() != null)
                    existingPermission.setAllowedRoles(permission.getAllowedRoles());

                // Update the timestamp for modification
                existingPermission.setUpdatedAt(LocalDateTime.now());

                permissionRepository.save(existingPermission);
                return ResponseEntity.ok(new CustomResponse<>("Permission updated successfully", null, true));
            } else {
                return ResponseEntity.status(404).body(new CustomResponse<>("Permission not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while updating the permission: " + e.getMessage(), null, false));
        }
    }

    /**
     * Delete a permission based on its unique ID.
     *
     * @param id The ID of the permission to be deleted.
     * @return A response entity indicating whether the deletion was successful or the permission was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deletePermission(@PathVariable String id) {
        try {
            Optional<Permission> existingPermissionOptional = permissionRepository.findById(id);
            if (existingPermissionOptional.isPresent()) {
                permissionRepository.deleteById(id);
                return ResponseEntity.ok(new CustomResponse<>("Permission deleted successfully", null, true));
            } else {
                return ResponseEntity.status(404).body(
                        new CustomResponse<>("Permission not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while deleting the permission: " + e.getMessage(), null, false));
        }
    }
}
