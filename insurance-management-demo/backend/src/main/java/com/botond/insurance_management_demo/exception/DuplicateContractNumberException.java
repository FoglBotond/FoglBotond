package com.botond.insurance_management_demo.exception;

public class DuplicateContractNumberException extends RuntimeException {
    public DuplicateContractNumberException(String contractNumber) {
        super("Insurance already exists for contract number: " + contractNumber);
    }
}
