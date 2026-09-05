package com.botond.insurance_management_demo.service;

import com.botond.insurance_management_demo.dto.InsuranceRequest;
import com.botond.insurance_management_demo.dto.InsuranceResponse;
import com.botond.insurance_management_demo.exception.DuplicateContractNumberException;
import com.botond.insurance_management_demo.exception.InsuranceNotFoundException;
import com.botond.insurance_management_demo.model.Insurance;
import com.botond.insurance_management_demo.model.InsuranceStatus;
import com.botond.insurance_management_demo.repository.InsuranceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InsuranceServiceTest {
    @Mock
    private InsuranceRepository insuranceRepository;

    @InjectMocks
    private InsuranceService insuranceService;

    private Insurance insurance;

    @BeforeEach
    void setUp() {
        insurance = new Insurance();
        insurance.setId(1L);
        insurance.setContractNumber("INS-2026-001");
        insurance.setCustomerName("John Doe");
        insurance.setProductName("Travel Insurance");
        insurance.setStartDate(LocalDate.of(2026, 9, 4));
        insurance.setEndDate(LocalDate.of(2027, 9, 4));
        insurance.setPremium(new BigDecimal("24990"));
        insurance.setStatus(InsuranceStatus.ACTIVE);
    }

    @Test
    void getInsuranceById_shouldReturnInsurance_whenInsuranceExists() {
        when(insuranceRepository.findById(1L))
                .thenReturn(Optional.of(insurance));

        InsuranceResponse result = insuranceService.getInsuranceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("INS-2026-001", result.getContractNumber());
        assertEquals("John Doe", result.getCustomerName());
        assertEquals(InsuranceStatus.ACTIVE, result.getStatus());

        verify(insuranceRepository).findById(1L);
    }

    @Test
    void getInsuranceById_shouldThrowException_whenInsuranceDoesNotExist() {
        when(insuranceRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                InsuranceNotFoundException.class,
                () -> insuranceService.getInsuranceById(999L)
        );

        verify(insuranceRepository).findById(999L);
    }

    @Test
    void createInsurance_shouldSaveInsurance_whenContractNumberIsUnique() {
        InsuranceRequest request = new InsuranceRequest();
        request.setContractNumber("INS-2026-002");
        request.setCustomerName("Jane Doe");
        request.setProductName("Home Insurance");
        request.setStartDate(LocalDate.of(2026, 9, 4));
        request.setEndDate(LocalDate.of(2027, 9, 4));
        request.setPremium(new BigDecimal("35000"));
        request.setStatus(InsuranceStatus.ACTIVE);

        when(insuranceRepository.existsByContractNumber("INS-2026-002"))
                .thenReturn(false);

        when(insuranceRepository.save(any(Insurance.class)))
                .thenAnswer(invocation -> {
                    Insurance saved = invocation.getArgument(0);
                    saved.setId(2L);
                    return saved;
                });

        InsuranceResponse result = insuranceService.createInsurance(request);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("INS-2026-002", result.getContractNumber());

        verify(insuranceRepository).existsByContractNumber("INS-2026-002");
        verify(insuranceRepository).save(any(Insurance.class));
    }

    @Test
    void createInsurance_shouldThrowException_whenContractNumberAlreadyExists() {
        InsuranceRequest request = new InsuranceRequest();
        request.setContractNumber("INS-2026-001");

        when(insuranceRepository.existsByContractNumber("INS-2026-001"))
                .thenReturn(true);

        assertThrows(
                DuplicateContractNumberException.class,
                () -> insuranceService.createInsurance(request)
        );

        verify(insuranceRepository).existsByContractNumber("INS-2026-001");
        verify(insuranceRepository, never()).save(any(Insurance.class));
    }

    @Test
    void updateInsurance_shouldUpdateInsurance_whenDataIsValid() {
        InsuranceRequest request = new InsuranceRequest();
        request.setContractNumber("INS-2026-001");
        request.setCustomerName("Updated Name");
        request.setProductName("Home Insurance");
        request.setStartDate(LocalDate.of(2026, 9, 4));
        request.setEndDate(LocalDate.of(2027, 9, 4));
        request.setPremium(new BigDecimal("42000"));
        request.setStatus(InsuranceStatus.ACTIVE);

        when(insuranceRepository.findById(1L))
                .thenReturn(Optional.of(insurance));

        when(insuranceRepository.existsByContractNumberAndIdNot(
                "INS-2026-001", 1L))
                .thenReturn(false);

        when(insuranceRepository.save(any(Insurance.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        InsuranceResponse result = insuranceService.updateInsurance(1L, request);

        assertEquals("Updated Name", result.getCustomerName());
        assertEquals("Home Insurance", result.getProductName());
        assertEquals(new BigDecimal("42000"), result.getPremium());

        verify(insuranceRepository).findById(1L);
        verify(insuranceRepository)
                .existsByContractNumberAndIdNot("INS-2026-001", 1L);
        verify(insuranceRepository).save(any(Insurance.class));
    }

    @Test
    void updateInsurance_shouldThrowException_whenContractNumberAlreadyExists() {
        InsuranceRequest request = new InsuranceRequest();
        request.setContractNumber("INS-2026-999");

        when(insuranceRepository.findById(1L))
                .thenReturn(Optional.of(insurance));

        when(insuranceRepository.existsByContractNumberAndIdNot(
                "INS-2026-999", 1L))
                .thenReturn(true);

        assertThrows(
                DuplicateContractNumberException.class,
                () -> insuranceService.updateInsurance(1L, request)
        );

        verify(insuranceRepository, never())
                .save(any(Insurance.class));
    }

    @Test
    void updateInsurance_shouldThrowException_whenInsuranceDoesNotExist() {
        when(insuranceRepository.findById(999L))
                .thenReturn(Optional.empty());

        InsuranceRequest request = new InsuranceRequest();

        assertThrows(
                InsuranceNotFoundException.class,
                () -> insuranceService.updateInsurance(999L, request)
        );

        verify(insuranceRepository, never())
                .save(any(Insurance.class));
    }
}
