package com.example.demo.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Payroll;
import com.example.demo.service.PayrollService;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "http://localhost:4200")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    private static final Logger logger = LoggerFactory.getLogger(PayrollController.class);

    // Endpoint to calculate and save payroll details
    @PostMapping
    public ResponseEntity<Payroll> calculatePayroll(@RequestBody PayrollDTO payrollDTO) {
        try {
            logger.info("Received payroll request: {}", payrollDTO);
            Payroll payroll = payrollService.calculateNetSalary(
                    payrollDTO.getEmployeeId(),
                    payrollDTO.getBaseSalary(),
                    payrollDTO.getBonuses(),
                    payrollDTO.getDeductions()
            );
            logger.info("Calculated payroll: {}", payroll);
            return new ResponseEntity<>(payroll, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error calculating payroll", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Inner static class for PayrollDTO
    public static class PayrollDTO {
        private Long employeeId;
        private BigDecimal baseSalary;
        private BigDecimal bonuses;
        private BigDecimal deductions;

        // Getters and Setters
        public Long getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }

        public BigDecimal getBaseSalary() {
            return baseSalary;
        }

        public void setBaseSalary(BigDecimal baseSalary) {
            this.baseSalary = baseSalary;
        }

        public BigDecimal getBonuses() {
            return bonuses;
        }

        public void setBonuses(BigDecimal bonuses) {
            this.bonuses = bonuses;
        }

        public BigDecimal getDeductions() {
            return deductions;
        }

        public void setDeductions(BigDecimal deductions) {
            this.deductions = deductions;
        }
    }
}
