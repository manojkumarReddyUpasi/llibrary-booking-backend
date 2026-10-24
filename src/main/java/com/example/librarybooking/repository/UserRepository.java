package com.example.librarybooking.repository;
import com.example.librarybooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import com.example.librarybooking.dto.ActiveUserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIgnoreCase(String email);
    @Query("SELECT new com.example.librarybooking.dto.ActiveUserDto(u.id, u.name, u.email, u.active) FROM User u WHERE u.active = true")
    List<ActiveUserDto> findActiveUserDtos();

    @EntityGraph(attributePaths = "roles")
    @Query("SELECT u FROM User u")
    List<User> findAllWithRoles();

    @Query("SELECT u.name FROM User u")
    List<String> getAllUsers();
}
