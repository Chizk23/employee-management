package com.chizk23.employee;

import com.chizk23.employee.controller.EmployeeController;
import com.chizk23.employee.dto.EmployeeRequestDTO;
import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllEmployees() throws Exception {
        Department it = new Department(1L, "IT");
        Employee emp = new Employee(1L, "Nguyen Van A", "a@example.com", it, 15000000.0);
        when(employeeService.getAllEmployees()).thenReturn(List.of(emp));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Nguyen Van A"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Department it = new Department(1L, "IT");
        Employee emp = new Employee(1L, "Nguyen Van A", "a@example.com", it, 15000000.0);
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(emp));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nguyen Van A"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeRequestDTO dto = new EmployeeRequestDTO("Le Van C", "c@example.com", 1L, 18000000.0);
        Employee created = new Employee(10L, "Le Van C", "c@example.com", new Department(1L, "IT"), 18000000.0);
        when(employeeService.createEmployee(any())).thenReturn(created);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Le Van C"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }
}
