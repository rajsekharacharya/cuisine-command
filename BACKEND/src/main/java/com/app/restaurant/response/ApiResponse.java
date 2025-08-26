package com.app.restaurant.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private Boolean status;
    private String message;
    private String error;
    private T data;
}