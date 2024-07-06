package com.gigglegazette.user_service.controller;

import com.gigglegazette.user_service.model.User;
import com.gigglegazette.user_service.repository.UserRepository;
import com.gigglegazette.user_service.util.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieve all users from the database.
     *
     * @return A response entity containing a list of all users and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(
                    new CustomResponse<>("Users retrieved successfully", users, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving users: " + e.getMessage(), null, false));
        }
    }

    /**
     * Fetch a specific user by their unique ID.
     *
     * @param id The ID of the user to be fetched.
     * @return A response entity containing the user if found, or a 'not found' message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<User>> getUserById(@PathVariable String id) {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.map(value -> ResponseEntity.ok(new CustomResponse<>("User retrieved successfully", value, true)))
                    .orElseGet(() -> ResponseEntity.status(404).body(
                            new CustomResponse<>("User not found", null, false)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving the user: " + e.getMessage(), null, false));
        }
    }

    /**
     * Fetch a specific user by their unique email.
     *
     * @param email The Email of the user to be fetched.
     * @return A response entity containing the user if found, or a 'not found' message.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomResponse<User>> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            return user.map(value -> ResponseEntity.ok(new CustomResponse<>("User retrieved successfully", value, true)))
                    .orElseGet(() -> ResponseEntity.status(404).body(
                            new CustomResponse<>("User not found", null, false)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving the user: " + e.getMessage(), null, false));
        }
    }

    /**
     * Fetch a specific user by their unique username.
     *
     * @param username The username of the user to be fetched.
     * @return A response entity containing the user if found, or a 'not found' message.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<CustomResponse<User>> getUserByUsername(@PathVariable String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            return user.map(value -> ResponseEntity.ok(new CustomResponse<>("User retrieved successfully", value, true)))
                    .orElseGet(() -> ResponseEntity.status(404).body(
                            new CustomResponse<>("User not found", null, false)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving the user: " + e.getMessage(), null, false));
        }
    }


    /**
     * Create a new user with the details provided in the request body.
     *
     * @param user The user details to be saved.
     * @return A response entity indicating that the user was created successfully.
     */
    @PostMapping
    public ResponseEntity<?>
    createUser(@Valid @RequestBody User user, BindingResult result) {
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
            User savedUser = userRepository.save(user);
            return ResponseEntity.status(201).body(
                    new CustomResponse<>("User created successfully", savedUser, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while creating the user: " + e.getMessage(), null, false));
        }
    }

    /**
     * Update an existing user's attributes based on the request body.
     * Only fields included in the request body will be updated.
     *
     * @param id   The ID of the user to be updated.
     * @param user The updated user details.
     * @return A response entity indicating whether the update was successful or the user was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<User>> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            Optional<User> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                // Only update fields that are provided in the request body
                if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
                if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
                if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
                if (user.getRole() != null) existingUser.setRole(user.getRole());
                if (user.getProfile() != null) existingUser.setProfile(user.getProfile());

                User savedUser = userRepository.save(existingUser);
                return ResponseEntity.ok(new CustomResponse<>("User updated successfully", savedUser, true));
            } else {
                return ResponseEntity.status(404).body(new CustomResponse<>("User not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while updating the user: " + e.getMessage(), null, false));
        }
    }

    /**
     * Delete a user based on their unique ID.
     *
     * @param id The ID of the user to be deleted.
     * @return A response entity indicating whether the deletion was successful or the user was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteUser(@PathVariable String id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok(new CustomResponse<>("User deleted successfully", null, true));
            } else {
                return ResponseEntity.status(404).body(
                        new CustomResponse<>("User not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while deleting the user: " + e.getMessage(), null, false));
        }
    }
}
