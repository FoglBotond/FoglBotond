package com.botond.insurance_management_demo.service;

import com.botond.insurance_management_demo.dto.InsuranceRequest;
import com.botond.insurance_management_demo.dto.InsuranceResponse;
import com.botond.insurance_management_demo.exception.DuplicateContractNumberException;
import com.botond.insurance_management_demo.exception.InsuranceNotFoundException;
import com.botond.insurance_management_demo.mapper.InsuranceMapper;
import com.botond.insurance_management_demo.model.Insurance;
import com.botond.insurance_management_demo.model.InsuranceStatus;
import com.botond.insurance_management_demo.repository.InsuranceRepository;
import com.botond.insurance_management_demo.specification.InsuranceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<Insurance> getAllInsurances() {
        return insuranceRepository.findAll();
    }

    public InsuranceResponse createInsurance(InsuranceRequest req) {
        if (insuranceRepository.existsByContractNumber(req.getContractNumber())) {
            throw new DuplicateContractNumberException(req.getContractNumber());
        }
        Insurance ins = InsuranceMapper.toEntity(req);

        Insurance savedIns = insuranceRepository.save(ins);

        return InsuranceMapper.toResponse(savedIns);
    }

    public InsuranceResponse getInsuranceById(Long id) {
        return InsuranceMapper.toResponse(findInsuranceById(id));
    }

    public InsuranceResponse updateInsurance(Long id, InsuranceRequest request) {
        Insurance existingInsurance = findInsuranceById(id);

        if (insuranceRepository.existsByContractNumberAndIdNot(
                request.getContractNumber(), id)) {
            throw new DuplicateContractNumberException(request.getContractNumber());
        }

        existingInsurance.setContractNumber(request.getContractNumber());
        existingInsurance.setCustomerName(request.getCustomerName());
        existingInsurance.setStartDate(request.getStartDate());
        existingInsurance.setEndDate(request.getEndDate());
        existingInsurance.setPremium(request.getPremium());
        existingInsurance.setProductName(request.getProductName());
        existingInsurance.setStatus(request.getStatus());
        Insurance savedIns = insuranceRepository.save(existingInsurance);
        return InsuranceMapper.toResponse(savedIns);
    }

    public void deleteInsuranceById(Long id) {
        Insurance existingIns = findInsuranceById(id);
        insuranceRepository.delete(existingIns);
    }

    public List<Insurance> getInsurancesByStatus(InsuranceStatus status) {
        return insuranceRepository.findByStatus(status);
    }

    public List<Insurance> getInsuranceByCustomerName(String customerName) {
        return insuranceRepository.findByCustomerNameContainingIgnoreCase(customerName);
    }

    public List<Insurance> getInsuranceByContractNumber(String contractNumber) {
        return insuranceRepository.findByContractNumberContainingIgnoreCase(contractNumber);
    }

    public Page<InsuranceResponse> searchInsurances(InsuranceStatus status, String customerName, String contractNumber, Pageable pageable) {
        Specification<Insurance> specification =
                Specification
                        .where(InsuranceSpecification.hasStatus(status))
                        .and(InsuranceSpecification.customerNameContains(customerName))
                        .and(InsuranceSpecification.contractNumberContains(contractNumber));

        return insuranceRepository.findAll(specification, pageable)
                .map(InsuranceMapper::toResponse);
    }

    private Insurance findInsuranceById(Long id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new InsuranceNotFoundException(id));
    }
}
