package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.Payroll;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.PayrollRepository;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Method to calculate net salary
    public Payroll calculateNetSalary(Long employeeId, BigDecimal baseSalary, BigDecimal bonuses, BigDecimal deductions) {
        // Calculate net salary
        BigDecimal netSalary = baseSalary.add(bonuses).subtract(deductions);
        
        // Find the employee by ID
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        
        // Create a new Payroll entry
        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setBaseSalary(baseSalary);
        payroll.setBonuses(bonuses);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);
        payroll.setPaymentDate(LocalDate.now());

        // Save the payroll entry in the database
        return payrollRepository.save(payroll);
    }
}