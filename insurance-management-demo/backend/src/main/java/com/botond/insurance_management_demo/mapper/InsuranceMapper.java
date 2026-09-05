package com.botond.insurance_management_demo.mapper;

import com.botond.insurance_management_demo.dto.InsuranceRequest;
import com.botond.insurance_management_demo.dto.InsuranceResponse;
import com.botond.insurance_management_demo.model.Insurance;

public class InsuranceMapper {
    public static Insurance toEntity(InsuranceRequest request) {
        Insurance insurance = new Insurance();

        insurance.setContractNumber(request.getContractNumber());
        insurance.setCustomerName(request.getCustomerName());
        insurance.setProductName(request.getProductName());
        insurance.setStartDate(request.getStartDate());
        insurance.setEndDate(request.getEndDate());
        insurance.setPremium(request.getPremium());
        insurance.setStatus(request.getStatus());

        return insurance;
    }

    public static InsuranceResponse toResponse(Insurance insurance) {
        InsuranceResponse response = new InsuranceResponse();

        response.setId(insurance.getId());
        response.setContractNumber(insurance.getContractNumber());
        response.setCustomerName(insurance.getCustomerName());
        response.setProductName(insurance.getProductName());
        response.setStartDate(insurance.getStartDate());
        response.setEndDate(insurance.getEndDate());
        response.setPremium(insurance.getPremium());
        response.setStatus(insurance.getStatus());

        return response;
    }
}
