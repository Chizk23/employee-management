package com.chizk23.employee.controller;

import com.chizk23.employee.dto.DepartmentStatisticsDTO;
import com.chizk23.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final EmployeeService employeeService;

    public AnalyticsController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentStatisticsDTO>> getDepartmentStatistics() {
        return ResponseEntity.ok(employeeService.getDepartmentStatistics());
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return ResponseEntity.ok(employeeService.getAnalyticsSummary());
    }
}
