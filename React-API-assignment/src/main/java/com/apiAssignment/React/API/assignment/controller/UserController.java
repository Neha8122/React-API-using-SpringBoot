package com.apiAssignment.React.API.assignment.controller;

import com.apiAssignment.React.API.assignment.dto.UserDTO;
import com.apiAssignment.React.API.assignment.model.User;
import com.apiAssignment.React.API.assignment.repository.UserRepository;
import com.apiAssignment.React.API.assignment.dto.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;




@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
        // Perform validation on userDTO fields
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required.");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }

        // Check if the username or email already exists
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        // Create a new User object from the UserDTO
        User user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail());

        // Store the user details in the H2 database
        userRepository.save(user);

        // Return a success response
        return ResponseEntity.ok("User signed up successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        // Perform validation on loginDTO fields
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required.");
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }

        // Check if the user exists in the database
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        // Validate the password
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        // Generate a JWT token for authentication
        String token = generateJwtToken(user.getUsername());

        // Return the JWT token in the response
        return ResponseEntity.ok(token);
    }

    private String generateJwtToken(String username) {
        // Generate a JWT token using a library like jjwt
        String secretKey = "yourSecretKey"; // Replace with your actual secret key
        String token = Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }
}

