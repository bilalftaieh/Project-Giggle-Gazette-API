package com.gigglegazette.user_service.controller;

import com.gigglegazette.user_service.model.Permission;
import com.gigglegazette.user_service.repository.PermissionRepository;
import com.gigglegazette.user_service.util.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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
     * Retrieve all permissions allowed for a specific role by role ID.
     *
     * @param roleId The ID of the role for which to fetch permissions.
     * @return A response entity containing a list of permissions for the role and a success message.
     */
    @GetMapping("/roles/{roleId}")
    public ResponseEntity<CustomResponse<List<Permission>>> getPermissionsByRoleId(@PathVariable String roleId) {
        try {
            List<Permission> permissions = permissionRepository.findByAllowedRolesId(roleId);
            if (!permissions.isEmpty()) {
                return ResponseEntity.ok(
                        new CustomResponse<>("Permissions retrieved successfully", permissions, true));
            } else {
                return ResponseEntity.status(404).body(
                        new CustomResponse<>("No permissions found for the specified role", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving permissions: " + e.getMessage(), null, false));
        }
    }

    /**
     * Create a new permission with the details provided in the request body.
     *
     * @param permission The permission details to be saved.
     * @return A response entity indicating that the permission was created successfully.
     */
    @PostMapping
    public ResponseEntity<?> createPermission
    (@Valid @RequestBody Permission permission, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            List<Map<String, String>> errorDetails = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                Map<String, String> errorDetail = new HashMap<>();
                errorDetail.put("field", error.getField());
                errorDetail.put("message", error.getDefaultMessage());
                errorDetails.add(errorDetail);
            }
            return ResponseEntity.status(400).body(
                    new CustomResponse<>("Validation Failed", errorDetails, false)
            );
        }
        try {
            Permission savedPermission = permissionRepository.save(permission);
            return ResponseEntity.status(201).body(
                    new CustomResponse<>("Permission created successfully", savedPermission, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while creating the permission: " + e.getMessage(), null, false));
        }
    }

    /**
     * Update an existing permission's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id         The ID of the permission to be updated.
     * @param permission The updated permission details.
     * @return A response entity indicating whether the update was successful or the permission was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Permission>> updatePermission(@PathVariable String id, @RequestBody Permission permission) {
        try {
            Optional<Permission> existingPermissionOptional = permissionRepository.findById(id);
            if (existingPermissionOptional.isPresent()) {
                Permission existingPermission = existingPermissionOptional.get();

                // Update fields from the request body
                if (permission.getName() != null) existingPermission.setName(permission.getName());
                if (permission.getAllowedRoles() != null)
                    existingPermission.setAllowedRoles(permission.getAllowedRoles());
                Permission savedPermission = permissionRepository.save(existingPermission);
                return ResponseEntity.ok(new CustomResponse<>("Permission updated successfully", savedPermission, true));
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
