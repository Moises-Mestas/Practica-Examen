package com.example.msauth.repository;

import com.example.msauth.dto.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    // Devolver Optional para manejar casos de ausencia del usuario
    Optional<AuthUser> findByUserName(String userName);
}
