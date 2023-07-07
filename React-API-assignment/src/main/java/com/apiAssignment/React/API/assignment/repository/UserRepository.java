package com.apiAssignment.React.API.assignment.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.apiAssignment.React.API.assignment.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Additional custom methods if needed
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);

}
