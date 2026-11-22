package com.chizk23.employee.config;

import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.model.User;
import com.chizk23.employee.repository.DepartmentRepository;
import com.chizk23.employee.repository.EmployeeRepository;
import com.chizk23.employee.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(DepartmentRepository departmentRepository,
                           EmployeeRepository employeeRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (departmentRepository.count() == 0) {
            Department it = departmentRepository.save(new Department(null, "IT"));
            Department hr = departmentRepository.save(new Department(null, "HR"));
            Department fin = departmentRepository.save(new Department(null, "Finance"));
            Department mkt = departmentRepository.save(new Department(null, "Marketing"));

            if (employeeRepository.count() == 0) {
                employeeRepository.saveAll(List.of(
                        new Employee(null, "Nguyen Van A", "vana@example.com", it, 18000000.0),
                        new Employee(null, "Tran Thi B", "thib@example.com", hr, 15000000.0),
                        new Employee(null, "Le Van C", "vanc@example.com", fin, 22000000.0),
                        new Employee(null, "Pham Thi D", "thid@example.com", mkt, 16000000.0)
                ));
            }
        }

        if (userRepository.count() == 0) {
            userRepository.save(new User(null, "admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN"));
            userRepository.save(new User(null, "user", passwordEncoder.encode("user123"), "ROLE_USER"));
        }
    }
}
