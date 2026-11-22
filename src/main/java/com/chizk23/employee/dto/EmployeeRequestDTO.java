package com.chizk23.employee.dto;

public class EmployeeRequestDTO {
    private String name;
    private String email;
    private Long departmentId;
    private Double salary;

    public EmployeeRequestDTO() {}

    public EmployeeRequestDTO(String name, String email, Long departmentId, Double salary) {
        this.name = name;
        this.email = email;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
