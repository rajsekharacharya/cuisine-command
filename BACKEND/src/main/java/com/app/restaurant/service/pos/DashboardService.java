package com.app.restaurant.service.pos;

import java.time.LocalDate;
import java.util.Map;

public interface DashboardService {

    public Map<String, Object> getDashboardData(LocalDate startDate, LocalDate endDate);

}
