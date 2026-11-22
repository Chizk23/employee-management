package com.chizk23.employee.service;

import com.chizk23.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final List<Employee> employeeStore = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public EmployeeService() {
        // Sample in-memory data
        createEmployee(new Employee(null, "Nguyen Van A", "vana@example.com", "IT", 15000000.0));
        createEmployee(new Employee(null, "Tran Thi B", "thib@example.com", "HR", 12000000.0));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeStore.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Employee createEmployee(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(idGenerator.getAndIncrement());
        }
        employeeStore.add(employee);
        return employee;
    }

    public List<Employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEmployees();
        }
        String lower = keyword.toLowerCase();
        return employeeStore.stream()
                .filter(e -> (e.getName() != null && e.getName().toLowerCase().contains(lower)) ||
                             (e.getDepartment() != null && e.getDepartment().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }
}
