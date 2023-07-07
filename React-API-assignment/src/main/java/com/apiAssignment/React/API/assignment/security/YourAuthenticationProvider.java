package com.apiAssignment.React.API.assignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.apiAssignment.React.API.assignment.model.User;
import com.apiAssignment.React.API.assignment.repository.UserRepository;

public class YourAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Autowired
    public YourAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Retrieve the username and password from the authentication object
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Implement your custom authentication logic here
        boolean isAuthenticated = authenticateUser(username, password);

        if (isAuthenticated) {
            // If the user is authenticated successfully, return a new authenticated token
            // using the provided username and password
            return new UsernamePasswordAuthenticationToken(username, password);
        } else {
            // If the user is not authenticated, throw an AuthenticationException or return null
            throw new AuthenticationException("Invalid username or password") {
                // You can customize the exception message or handle it differently if needed
            };
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Specify the authentication class that this provider supports
        // For example, UsernamePasswordAuthenticationToken
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

}
