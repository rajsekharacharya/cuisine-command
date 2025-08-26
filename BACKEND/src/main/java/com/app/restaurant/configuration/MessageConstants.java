package com.app.restaurant.configuration;

public class MessageConstants {

    // ==============================
    // General Success & Failure
    // ==============================
    public static final String OPERATION_SUCCESSFUL = "Operation completed successfully.";
    public static final String OPERATION_FAILED = "Operation could not be completed.";
    public static final String UNEXPECTED_ERROR_OCCURRED = "An unexpected error occurred.";
    public static final String PROCESSING_REQUEST = "Processing your request. Please wait...";
    public static final String ACTION_SUCCESS = "Action completed successfully.";
    public static final String ACTION_FAILURE = "Action failed. Please try again.";
    public static final String OPERATION_CANCELLED = "Operation was cancelled.";

    // ==============================
    // CRUD Operations
    // ==============================
    public static final String CREATED_SUCCESSFULLY = "Record created successfully.";
    public static final String FETCHED_SUCCESSFULLY = "Data fetched successfully.";
    public static final String UPDATED_SUCCESSFULLY = "Record updated successfully.";
    public static final String DELETED_SUCCESSFULLY = "Record deleted successfully.";
    public static final String TOGGLED_SUCCESSFULLY = "Status toggled successfully.";
    public static final String RECORD_ALREADY_EXISTS = "A record with the same information already exists.";
    public static final String RECORD_NOT_FOUND = "Record not found.";
    public static final String NO_CONTENT_AVAILABLE = "No content available.";
    public static final String CONFLICT_DETECTED = "Conflict detected. Please check your request.";

    // ==============================
    // Common Error Messages
    // ==============================
    public static final String COMMON_NOT_FOUND = "Requested resource not found.";
    public static final String COMMON_INVALID = "Invalid request or parameters.";
    public static final String COMMON_UNAUTHORIZED = "You are not authorized to perform this action.";
    public static final String COMMON_FORBIDDEN = "Access is forbidden for this resource.";
    public static final String COMMON_BAD_REQUEST = "Bad request. Please check your input.";
    public static final String COMMON_SERVER_ERROR = "An internal server error occurred.";
    public static final String DATABASE_ERROR = "A database error occurred.";

    // ==============================
    // Authentication & Authorization
    // ==============================
    public static final String AUTHENTICATION_FAILED = "Authentication failed.";
    public static final String ACCESS_DENIED = "Access denied.";
    public static final String INVALID_ARGUMENT = "Invalid argument.";
    public static final String INVALID_ACCESS_TOKEN = "Invalid or expired access token.";
    public static final String INVALID_REFRESH_TOKEN = "Invalid or expired refresh token.";
    public static final String INVALID_CREDENTIALS = "Invalid username or password.";
    public static final String JWT_EXPIRED = "JWT token has expired.";
    public static final String JWT_ERROR = "An error occurred with the JWT token.";

    // ==============================
    // Validation
    // ==============================
    public static final String VALIDATION_FAILED = "Validation failed.";
    public static final String VALIDATION_ERRORS_OCCURRED = "Validation errors occurred.";
    public static final String MISSING_REQUIRED_FIELDS = "Some required fields are missing.";
    public static final String INVALID_INPUT = "Invalid input provided.";

    // ==============================
    // OTP & Token
    // ==============================
    public static final String OTP_SENT = "OTP has been sent successfully.";
    public static final String OTP_EXPIRED = "The OTP has expired. Please request a new one.";
    public static final String OTP_INVALID = "Invalid OTP provided.";
    public static final String OTP_NOT_FOUND = "No OTP found for this request.";
    public static final String OTP_VERIFIED = "OTP verified successfully.";
    public static final String OTP_RESEND = "A new OTP has been sent.";

    // ==============================
    // User Management
    // ==============================
    public static final String USER_CREATED = "User account created successfully.";
    public static final String USER_UPDATED = "User account updated successfully.";
    public static final String USER_DELETED = "User account deleted successfully.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String USER_LISTED = "User list retrieved successfully.";

    // ==============================
    // Service Availability
    // ==============================
    public static final String SERVICE_UNAVAILABLE = "Service is currently unavailable. Please try again later.";

    private MessageConstants() {
        // Private constructor to prevent instantiation
    }
}
