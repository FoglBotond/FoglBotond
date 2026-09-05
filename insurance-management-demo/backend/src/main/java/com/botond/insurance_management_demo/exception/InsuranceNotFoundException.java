package com.botond.insurance_management_demo.exception;

public class InsuranceNotFoundException extends RuntimeException {
    public InsuranceNotFoundException(Long id) {
        super("Insurance not found with id " + id);
    }
}
