package com.chizk23.employee.repository;

import com.chizk23.employee.dto.DepartmentStatisticsDTO;
import com.chizk23.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartmentNameContainingIgnoreCase(String departmentName);

    List<Employee> findByNameContainingIgnoreCaseOrDepartmentNameContainingIgnoreCase(String name, String departmentName);

    @Query("SELECT new com.chizk23.employee.dto.DepartmentStatisticsDTO(coalesce(d.name, 'Chưa phân bổ'), COUNT(e), coalesce(AVG(e.salary), 0.0)) " +
           "FROM Employee e LEFT JOIN e.department d GROUP BY d.name ORDER BY COUNT(e) DESC")
    List<DepartmentStatisticsDTO> countEmployeesByDepartment();

    @Query("SELECT COUNT(e) FROM Employee e")
    long countAllEmployeesTotal();
}
