package com.botond.insurance_management_demo.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> fieldErrors;

    public ValidationErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            Map<String, String> fieldErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.fieldErrors = fieldErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
