package com.app.restaurant.repository.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.restaurant.model.admin.LoginHistory;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    Optional<LoginHistory> findByTokenAndIsActive(String token, Boolean isActive);

}
