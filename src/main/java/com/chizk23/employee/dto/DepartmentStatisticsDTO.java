package com.chizk23.employee.dto;

public class DepartmentStatisticsDTO {
    private String departmentName;
    private Long employeeCount;
    private Double averageSalary;

    public DepartmentStatisticsDTO() {}

    public DepartmentStatisticsDTO(String departmentName, Long employeeCount, Double averageSalary) {
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }
}
