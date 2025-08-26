package com.app.restaurant.serviceimpl.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.restaurant.dto.admin.LoginHistoryDTO;
import com.app.restaurant.enums.LoginStatus;
import com.app.restaurant.model.admin.LoginHistory;
import com.app.restaurant.model.admin.Users;
import com.app.restaurant.repository.admin.LoginHistoryRepository;
import com.app.restaurant.service.admin.LoginHistoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(LoginHistoryServiceImpl.class);

    private final LoginHistoryRepository repository;

    @Transactional
    public void saveLoginHistory(Users user, String token, String ipAddress, String device, LoginStatus status) {
        logger.debug("Saving login history for user: {}, status: {}", user.getEmail(), status);
        LoginHistory history = LoginHistory.builder()
                .user(user)
                .token(token)
                .ipAddress(ipAddress)
                .deviceInfo(device)
                .status(status)
                .isActive(status == LoginStatus.SUCCESS)
                .loginTime(LocalDateTime.now())
                .build();
        repository.save(history);
    }

    @Transactional
    public void updateLogout(String token) {
        logger.debug("Updating logout for token: {}", token);
        repository.findByTokenAndIsActive(token, true).ifPresent(history -> {
            history.setLogoutTime(LocalDateTime.now());
            history.setIsActive(false);
            repository.save(history);
            logger.debug("Logout updated for user: {}", history.getUser().getEmail());
        });
    }

    @Override
    public List<LoginHistoryDTO> getAllHistory() {
        logger.debug("Fetching all login histories");
        return repository.findAll().stream()
                .map(history -> LoginHistoryDTO.builder()
                        .ipAddress(history.getIpAddress())
                        .deviceInfo(history.getDeviceInfo())
                        .loginTime(history.getLoginTime())
                        .logoutTime(history.getLogoutTime())
                        .user(history.getUser() != null ? history.getUser().getName() : null)
                        .status(history.getStatus() != null ? history.getStatus().toString() : null)
                        .build())
                .toList();
    }
}
