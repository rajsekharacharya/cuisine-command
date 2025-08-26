package com.app.restaurant.dto.admin;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginHistoryDTO {
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String user;
    private String status;
}
