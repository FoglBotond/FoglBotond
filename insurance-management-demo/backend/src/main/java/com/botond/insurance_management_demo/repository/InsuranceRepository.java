package com.botond.insurance_management_demo.repository;

import com.botond.insurance_management_demo.model.Insurance;
import com.botond.insurance_management_demo.model.InsuranceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Long>, JpaSpecificationExecutor<Insurance> {
    List<Insurance> findByStatus(InsuranceStatus status);

    List<Insurance> findByCustomerNameContainingIgnoreCase(String customerName);

    List<Insurance> findByContractNumberContainingIgnoreCase(String contractNumber);

    boolean existsByContractNumber(String contractNumber);

    boolean existsByContractNumberAndIdNot(String contractNumber, Long id);
}
