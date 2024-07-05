package com.gigglegazette.user_service.controller;

import com.gigglegazette.user_service.model.Role;
import com.gigglegazette.user_service.repository.RoleRepository;
import com.gigglegazette.user_service.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Retrieve all roles from the database.
     *
     * @return A response entity containing a list of all roles and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<Role>>> getAllRoles() {
        try {
            List<Role> roles = roleRepository.findAll();
            return ResponseEntity.ok(
                    new CustomResponse<>("Roles retrieved successfully", roles, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving roles: " + e.getMessage(), null, false));
        }
    }

    /**
     * Fetch a specific role by its unique ID.
     *
     * @param id The ID of the role to be fetched.
     * @return A response entity containing the role if found, or a 'not found' message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Role>> getRoleById(@PathVariable String id) {
        try {
            Optional<Role> role = roleRepository.findById(id);
            return role.map(value -> ResponseEntity.ok(
                            new CustomResponse<>("Role retrieved successfully", value, true)))
                    .orElseGet(() -> ResponseEntity.status(404).body(
                            new CustomResponse<>("Role not found", null, false)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving the role: " + e.getMessage(), null, false));
        }
    }

    /**
     * Create a new role with the details provided in the request body.
     *
     * @param role The role details to be saved.
     * @return A response entity indicating that the role was created successfully.
     */
    @PostMapping
    public ResponseEntity<CustomResponse<String>> createRole(@RequestBody Role role) {
        try {
            roleRepository.save(role);
            return ResponseEntity.status(201).body(
                    new CustomResponse<>("Role created successfully", null, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while creating the role: " + e.getMessage(), null, false));
        }
    }

    /**
     * Update an existing role's attributes based on the request body.
     * Only fields included in the request body will be updated.
     *
     * @param id The ID of the role to be updated.
     * @param role The updated role details.
     * @return A response entity indicating whether the update was successful or the role was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> updateRole(@PathVariable String id, @RequestBody Role role) {
        try {
            Optional<Role> existingRoleOptional = roleRepository.findById(id);
            if (existingRoleOptional.isPresent()) {
                Role existingRole = existingRoleOptional.get();
                // Only update fields that are provided in the request body
                if (role.getName() != null) existingRole.setName(role.getName());
                if (role.getUpdatedAt() != null) existingRole.setUpdatedAt(role.getUpdatedAt());
                roleRepository.save(existingRole);
                return ResponseEntity.ok(new CustomResponse<>("Role updated successfully", null, true));
            } else {
                return ResponseEntity.status(404).body(new CustomResponse<>("Role not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while updating the role: " + e.getMessage(), null, false));
        }
    }

    /**
     * Delete a role based on its unique ID.
     *
     * @param id The ID of the role to be deleted.
     * @return A response entity indicating whether the deletion was successful or the role was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteRole(@PathVariable String id) {
        try {
            Optional<Role> roleOptional = roleRepository.findById(id);
            if (roleOptional.isPresent()) {
                roleRepository.deleteById(id);
                return ResponseEntity.ok(new CustomResponse<>("Role deleted successfully", null, true));
            } else {
                return ResponseEntity.status(404).body(
                        new CustomResponse<>("Role not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while deleting the role: " + e.getMessage(), null, false));
        }
    }
}
