// UserRepository.java
package com.example.college_explorer.repository;

import com.example.college_explorer.model.User;
import com.example.college_explorer.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(UserRole role);
}
