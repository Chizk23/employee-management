package com.chizk23.employee.service;

import com.chizk23.employee.dto.EmployeeRequestDTO;
import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.repository.DepartmentRepository;
import com.chizk23.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(EmployeeRequestDTO dto) {
        Department department = null;
        if (dto.getDepartmentId() != null) {
            department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);
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

    public Optional<Employee> updateEmployee(Long id, EmployeeRequestDTO dto) {
        return employeeRepository.findById(id).map(emp -> {
            emp.setName(dto.getName());
            emp.setEmail(dto.getEmail());
            emp.setSalary(dto.getSalary());
            if (dto.getDepartmentId() != null) {
                Department department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);
                emp.setDepartment(department);
            }
            return employeeRepository.save(emp);
        });
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }
        return employeeRepository.findByNameContainingIgnoreCaseOrDepartmentNameContainingIgnoreCase(keyword.trim(), keyword.trim());
    }
}
