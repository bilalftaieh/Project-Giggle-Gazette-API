package com.gigglegazette.auth_service.client;

import com.gigglegazette.auth_service.dto.CustomResponse;
import com.gigglegazette.auth_service.dto.Permission;
import com.gigglegazette.auth_service.dto.UserData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;

@HttpExchange
public interface UserClient {
    @GetExchange("/users/{id}")
    ResponseEntity<?> getUserById(@PathVariable String id);

    @GetExchange("/users/email/{email}")
    ResponseEntity<CustomResponse<UserData>> getUserByEmail(@PathVariable String email);

    @GetExchange("/users/username/{username}")
    ResponseEntity<CustomResponse<UserData>> getUserByUsername(@PathVariable String username);

    @PostExchange("/users")
    ResponseEntity<CustomResponse<UserData>> createUser(@RequestBody UserData user);

    @GetExchange("permissions/roles/{roleId}")
    ResponseEntity<CustomResponse<List<Permission>>> getPermissionsByRoleId(@PathVariable String roleId);
}
