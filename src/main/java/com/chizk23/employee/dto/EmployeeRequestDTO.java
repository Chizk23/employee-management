package com.chizk23.employee.dto;

import jakarta.validation.constraints.*;

public class EmployeeRequestDTO {

    @NotBlank(message = "Employee name cannot be blank")
    @Size(min = 2, max = 100, message = "Employee name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    private String email;

    private Long departmentId;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero(message = "Salary must be greater than or equal to 0")
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
