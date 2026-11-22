package com.chizk23.employee.service;

import com.chizk23.employee.dto.EmployeeRequestDTO;
import com.chizk23.employee.exception.ResourceNotFoundException;
import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.repository.DepartmentRepository;
import com.chizk23.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee createEmployee(EmployeeRequestDTO dto) {
        Department department = null;
        if (dto.getDepartmentId() != null) {
            department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
        }

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    public Employee saveEmployeeEntity(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee emp = getEmployeeById(id);
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setSalary(dto.getSalary());
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            emp.setDepartment(department);
        }
        return employeeRepository.save(emp);
    }

    public void deleteEmployee(Long id) {
        Employee emp = getEmployeeById(id);
        employeeRepository.delete(emp);
    }

    public List<Employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }
        return employeeRepository.findByNameContainingIgnoreCaseOrDepartmentNameContainingIgnoreCase(keyword.trim(), keyword.trim());
    }
}
