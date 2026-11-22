package com.chizk23.employee;

import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.repository.DepartmentRepository;
import com.chizk23.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testSaveAndFindEmployeeWithDepartment() {
        Department it = departmentRepository.save(new Department(null, "IT"));

        Employee emp = new Employee(null, "Tran Van Dung", "dung@example.com", it, 20000000.0);
        Employee saved = employeeRepository.save(emp);

        assertNotNull(saved.getId());
        assertEquals("IT", saved.getDepartment().getName());

        List<Employee> results = employeeRepository.findByNameContainingIgnoreCaseOrDepartmentNameContainingIgnoreCase("Dung", "Dung");
        assertFalse(results.isEmpty());
        assertEquals("Tran Van Dung", results.get(0).getName());
    }
}
