package com.app.restaurant.service.admin;

import java.util.List;

import com.app.restaurant.dto.admin.LoginHistoryDTO;
import com.app.restaurant.enums.LoginStatus;
import com.app.restaurant.model.admin.Users;

public interface LoginHistoryService {

    public void saveLoginHistory(Users user, String token, String ipAddress, String device, LoginStatus status);

    public void updateLogout(String token);

    public List<LoginHistoryDTO> getAllHistory();

}
