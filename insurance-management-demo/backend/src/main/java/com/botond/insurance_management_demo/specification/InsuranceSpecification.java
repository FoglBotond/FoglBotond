package com.botond.insurance_management_demo.specification;

import com.botond.insurance_management_demo.model.Insurance;
import com.botond.insurance_management_demo.model.InsuranceStatus;
import org.springframework.data.jpa.domain.Specification;

public class InsuranceSpecification {

    public static Specification<Insurance> hasStatus(InsuranceStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null
                        ? null
                        : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Insurance> customerNameContains(String customerName) {
        return (root, query, criteriaBuilder) ->
                customerName == null || customerName.isBlank()
                        ? null
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("customerName")),
                        "%" + customerName.toLowerCase() + "%"
                );
    }

    public static Specification<Insurance> contractNumberContains(String contractNumber) {
        return (root, query, criteriaBuilder) ->
                contractNumber == null || contractNumber.isBlank()
                        ? null
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("contractNumber")),
                        "%" + contractNumber.toLowerCase() + "%"
                );
    }
}
