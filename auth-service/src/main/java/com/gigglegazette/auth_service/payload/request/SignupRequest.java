package com.gigglegazette.auth_service.payload.request;

import java.util.Set;

import com.gigglegazette.auth_service.dto.Profie;
import com.gigglegazette.auth_service.dto.Role;
import jakarta.validation.constraints.*;

public class SignupRequest {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Role is mandatory")
    private Role role;

    @NotNull(message = "Profile is mandatory")
    private Profie profile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public @NotNull(message = "Profile is mandatory") Profie getProfile() {
        return profile;
    }

    public void setProfile(@NotNull(message = "Profile is mandatory") Profie profile) {
        this.profile = profile;
    }
}
