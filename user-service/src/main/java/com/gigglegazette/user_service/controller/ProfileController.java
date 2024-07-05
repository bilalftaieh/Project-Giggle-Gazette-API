package com.gigglegazette.user_service.controller;

import com.gigglegazette.user_service.model.Profile;
import com.gigglegazette.user_service.repository.ProfileRepository;
import com.gigglegazette.user_service.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    /**
     * Retrieve all profiles from the database.
     *
     * @return A response entity containing a list of all profiles and a success message.
     */
    @GetMapping
    public ResponseEntity<CustomResponse<List<Profile>>> getAllProfiles() {
        try {
            List<Profile> profiles = profileRepository.findAll();
            return ResponseEntity.ok(
                    new CustomResponse<>("Profiles retrieved successfully", profiles, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving profiles: " + e.getMessage(), null, false));
        }
    }

    /**
     * Fetch a specific profile by its unique ID.
     *
     * @param id The ID of the profile to be fetched.
     * @return A response entity containing the profile if found, or a 'not found' message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Profile>> getProfileById(@PathVariable String id) {
        try {
            Optional<Profile> profile = profileRepository.findById(id);
            return profile.map(value -> ResponseEntity.ok(
                            new CustomResponse<>("Profile retrieved successfully", value, true)))
                    .orElseGet(() -> ResponseEntity.status(404).body(
                            new CustomResponse<>("Profile not found", null, false)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while retrieving the profile: " + e.getMessage(), null, false));
        }
    }

    /**
     * Create a new profile with the details provided in the request body.
     *
     * @param profile The profile details to be saved.
     * @return A response entity indicating that the profile was created successfully.
     */
    @PostMapping
    public ResponseEntity<CustomResponse<String>> createProfile(@RequestBody Profile profile) {
        try {
            profile.setCreatedAt(LocalDateTime.now());
            profile.setUpdatedAt(LocalDateTime.now());
            profileRepository.save(profile);
            return ResponseEntity.status(201).body(
                    new CustomResponse<>("Profile created successfully", null, true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while creating the profile: " + e.getMessage(), null, false));
        }
    }

    /**
     * Update an existing profile's attributes based on the request body.
     * Only the fields included in the request body will be updated.
     *
     * @param id The ID of the profile to be updated.
     * @param profile The updated profile details.
     * @return A response entity indicating whether the update was successful or the profile was not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> updateProfile(@PathVariable String id, @RequestBody Profile profile) {
        try {
            Optional<Profile> existingProfileOptional = profileRepository.findById(id);
            if (existingProfileOptional.isPresent()) {
                Profile existingProfile = existingProfileOptional.get();

                // Update fields from the request body
                if (profile.getFirstName() != null) existingProfile.setFirstName(profile.getFirstName());
                if (profile.getLastName() != null) existingProfile.setLastName(profile.getLastName());
                if (profile.getDateOfBirth() != null) existingProfile.setDateOfBirth(profile.getDateOfBirth());
                if (profile.getAddress() != null) existingProfile.setAddress(profile.getAddress());
                if (profile.getPhoneNumber() != null) existingProfile.setPhoneNumber(profile.getPhoneNumber());
                if (profile.getProfilePicture() != null) existingProfile.setProfilePicture(profile.getProfilePicture());

                // Update the timestamp for modification
                existingProfile.setUpdatedAt(LocalDateTime.now());

                profileRepository.save(existingProfile);
                return ResponseEntity.ok(new CustomResponse<>("Profile updated successfully", null, true));
            } else {
                return ResponseEntity.status(404).body(new CustomResponse<>("Profile not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while updating the profile: " + e.getMessage(), null, false));
        }
    }

    /**
     * Delete a profile based on its unique ID.
     *
     * @param id The ID of the profile to be deleted.
     * @return A response entity indicating whether the deletion was successful or the profile was not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteProfile(@PathVariable String id) {
        try {
            Optional<Profile> existingProfileOptional = profileRepository.findById(id);
            if (existingProfileOptional.isPresent()) {
                profileRepository.deleteById(id);
                return ResponseEntity.ok(new CustomResponse<>("Profile deleted successfully", null, true));
            } else {
                return ResponseEntity.status(404).body(
                        new CustomResponse<>("Profile not found", null, false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new CustomResponse<>("An error occurred while deleting the profile: " + e.getMessage(), null, false));
        }
    }
}
