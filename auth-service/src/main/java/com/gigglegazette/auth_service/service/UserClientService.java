package com.gigglegazette.auth_service.service;

import com.gigglegazette.auth_service.client.UserClient;
import com.gigglegazette.auth_service.dto.CustomResponse;
import com.gigglegazette.auth_service.dto.Permission;
import com.gigglegazette.auth_service.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class UserClientService {

    @Autowired
    private UserClient userClient;

    public List<Permission> getPermissionsByRoleId(String roleId) {
        try {
            ResponseEntity<CustomResponse<List<Permission>>> response = userClient.getPermissionsByRoleId(roleId);
            CustomResponse<List<Permission>> permissionResponse = response.getBody();
            return permissionResponse.getData();
        } catch (WebClientResponseException.NotFound ex) {
            // Handle 404 error (resource not found)
            return null; // Return null when permissions not found
        }

    }

    public UserData getUserByUsername(String username) {
        try {
            ResponseEntity<CustomResponse<UserData>> response = userClient.getUserByUsername(username);
            CustomResponse<UserData> userResponse = response.getBody();
            return userResponse.getData();
        } catch (WebClientResponseException.NotFound ex) {
            // Handle 404 error (resource not found)
            // You can log the error or take other appropriate actions
            return null; // Return null when user not found
        }
    }

    public UserData getUserByEmail(String email) {
        try {
            ResponseEntity<CustomResponse<UserData>> response = userClient.getUserByEmail(email);
            CustomResponse<UserData> userResponse = response.getBody();
            return userResponse.getData();
        } catch (WebClientResponseException.NotFound ex) {
            // Handle 404 error (resource not found)
            // You can log the error or take other appropriate actions
            return null; // Return null when user not found
        }

    }


    public UserData createUser(UserData user) {
        ResponseEntity<CustomResponse<UserData>> response = userClient.createUser(user);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            CustomResponse<UserData> userResponse = response.getBody();
            if (userResponse != null && userResponse.getData() != null) {
                return userResponse.getData();
            } else {
                throw new RuntimeException("User data is null");
            }
        } else {
            throw new RuntimeException("Failed to create user");
        }
    }

}
