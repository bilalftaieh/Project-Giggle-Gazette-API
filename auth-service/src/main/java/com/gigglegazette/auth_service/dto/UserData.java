package com.gigglegazette.auth_service.dto;

public class UserData {
    private String id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private Profie profile;
    private String createdAt;
    private String updatedAt;

    public UserData(String username, String email, String password, Role role, Profie profile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Profie getProfile() {
        return profile;
    }

    public void setProfile(Profie profile) {
        this.profile = profile;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
