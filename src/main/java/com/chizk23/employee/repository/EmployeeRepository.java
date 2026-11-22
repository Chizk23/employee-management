package com.chizk23.employee.repository;

import com.chizk23.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartmentNameContainingIgnoreCase(String departmentName);

    List<Employee> findByNameContainingIgnoreCaseOrDepartmentNameContainingIgnoreCase(String name, String departmentName);
}
