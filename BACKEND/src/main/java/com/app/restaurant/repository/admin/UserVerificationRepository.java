package com.app.restaurant.repository.admin;

import com.app.restaurant.model.admin.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Integer> {
    Optional<UserVerification> findByToken(String token);
}