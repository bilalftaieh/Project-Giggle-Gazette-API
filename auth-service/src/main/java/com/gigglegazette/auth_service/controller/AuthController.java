package com.gigglegazette.auth_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.gigglegazette.auth_service.dto.Profie;
import com.gigglegazette.auth_service.dto.Role;
import com.gigglegazette.auth_service.dto.UserData;
import com.gigglegazette.auth_service.payload.request.LoginRequest;
import com.gigglegazette.auth_service.payload.request.SignupRequest;
import com.gigglegazette.auth_service.payload.response.CustomResponse;
import com.gigglegazette.auth_service.payload.response.JwtResponse;
import com.gigglegazette.auth_service.security.jwt.JwtUtils;
import com.gigglegazette.auth_service.security.services.UserDetailsImpl;
import com.gigglegazette.auth_service.service.UserClientService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Dependency injections
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserClientService userClientService;

    /**
     * Authenticates a user based on login credentials and returns a JWT token upon success.
     *
     * @param loginRequest The login request payload containing username and password.
     * @return ResponseEntity containing JWT token and user details.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Set authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Retrieve user details and roles
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Return JWT response
            return new ResponseEntity<>(
                    new CustomResponse<>("User Successfully Logged In!",
                            new JwtResponse(jwt, userDetails.getId(),
                                    userDetails.getUsername(), userDetails.getEmail(), roles), true),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Authentication Failed: " + e.getMessage(), null, false), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Registers a new user with the provided signup details.
     *
     * @param signUpRequest The signup request payload containing user details.
     * @return ResponseEntity indicating the outcome of the registration process.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            // Check if username already exists
            UserData userFromUsername = userClientService.getUserByUsername(signUpRequest.getUsername());
            if (userFromUsername != null) {
                return new ResponseEntity<>(new CustomResponse<>("Error: Username is already taken!", null, false), HttpStatus.CONFLICT);
            }

            // Check if email already exists
            UserData userFromEmail = userClientService.getUserByEmail(signUpRequest.getEmail());
            if (userFromEmail != null) {
                return new ResponseEntity<>(new CustomResponse<>("Error: Email is already in use!", null, false), HttpStatus.CONFLICT);
            }

            // Extract role and profile from signup request
            Role role = signUpRequest.getRole();
            Profie profile = signUpRequest.getProfile();

            // Create a new user account
            UserData user = new UserData(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), role, profile);

            // Save the new user
            UserData savedUser = userClientService.createUser(user);

            // Return successful creation response
            return new ResponseEntity<>(new CustomResponse<>("User is Created!", savedUser, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>("Registration Failed: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
