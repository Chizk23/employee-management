package com.chizk23.employee.service;

import com.chizk23.employee.dto.EmployeeRequestDTO;
import com.chizk23.employee.exception.ResourceNotFoundException;
import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.repository.DepartmentRepository;
import com.chizk23.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees() {
        log.debug("Fetching all employees from database");
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        log.debug("Fetching employee by id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee with id {} was not found", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
    }

    @CacheEvict(value = "employeeCountReport", allEntries = true)
    public Employee createEmployee(EmployeeRequestDTO dto) {
        log.info("Creating new employee: name='{}', email='{}'", dto.getName(), dto.getEmail());
        Department department = null;
        if (dto.getDepartmentId() != null) {
            department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> {
                        log.warn("Department with id {} was not found", dto.getDepartmentId());
                        return new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId());
                    });
        }

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        Employee saved = employeeRepository.save(employee);
        log.info("Successfully created employee with id: {}", saved.getId());
        return saved;
    }

    @CacheEvict(value = "employeeCountReport", allEntries = true)
    public Employee saveEmployeeEntity(Employee employee) {
        log.info("Saving employee entity directly: id={}", employee.getId());
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employeeCountReport", allEntries = true)
    public Employee updateEmployee(Long id, EmployeeRequestDTO dto) {
        log.info("Updating employee with id: {}", id);
        Employee emp = getEmployeeById(id);
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setSalary(dto.getSalary());
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> {
                        log.warn("Department with id {} was not found", dto.getDepartmentId());
                        return new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId());
                    });
            emp.setDepartment(department);
        }
        Employee updated = employeeRepository.save(emp);
        log.info("Successfully updated employee with id: {}", updated.getId());
        return updated;
    }

    @CacheEvict(value = "employeeCountReport", allEntries = true)
    public void deleteEmployee(Long id) {
        log.warn("Deleting employee with id: {}", id);
        Employee emp = getEmployeeById(id);
        employeeRepository.delete(emp);
        log.info("Successfully deleted employee with id: {}", id);
    }

    public List<Employee> searchEmployees(String keyword) {
        log.debug("Searching employees by keyword: '{}'", keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }
        return employeeRepository.findByNameContainingIgnoreCaseOrDepartmentNameContainingIgnoreCase(keyword.trim(), keyword.trim());
    }

    @Cacheable(value = "employeeCountReport")
    public long countTotalEmployeesReport() {
        log.info("Computing total employee count directly from DB query (cache miss)...");
        return employeeRepository.count();
    }
}
