package com.botond.insurance_management_demo.controller;

import com.botond.insurance_management_demo.dto.InsuranceRequest;
import com.botond.insurance_management_demo.dto.InsuranceResponse;
import com.botond.insurance_management_demo.model.InsuranceStatus;
import com.botond.insurance_management_demo.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/insurances")
public class InsuranceController {

    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }
    @GetMapping
    public Page<InsuranceResponse> getInsurances(
            @RequestParam(required = false) InsuranceStatus status,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String contractNumber,
            Pageable pageable) {

        return insuranceService.searchInsurances(status, customerName, contractNumber, pageable);
    }

    @PostMapping
    public InsuranceResponse createInsurance(@Valid @RequestBody InsuranceRequest insurance) {
        return insuranceService.createInsurance(insurance);
    }

    @GetMapping("/{id}")
    public InsuranceResponse getInsuranceById(@PathVariable Long id) {
        return insuranceService.getInsuranceById(id);
    }

    @PutMapping("/{id}")
    public InsuranceResponse updateInsurance(@PathVariable Long id, @Valid @RequestBody InsuranceRequest request) {
        return insuranceService.updateInsurance(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInsuranceById(@PathVariable Long id) {
        insuranceService.deleteInsuranceById(id);
    }
}
