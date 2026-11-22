package com.chizk23.employee;

import com.chizk23.employee.config.SecurityConfig;
import com.chizk23.employee.controller.EmployeeController;
import com.chizk23.employee.dto.EmployeeRequestDTO;
import com.chizk23.employee.exception.GlobalExceptionHandler;
import com.chizk23.employee.exception.ResourceNotFoundException;
import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.security.CustomUserDetailsService;
import com.chizk23.employee.security.JwtTokenProvider;
import com.chizk23.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void testGetAllEmployees() throws Exception {
        Department it = new Department(1L, "IT");
        Employee emp = new Employee(1L, "Nguyen Van A", "a@example.com", it, 15000000.0);
        when(employeeService.getAllEmployees()).thenReturn(List.of(emp));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Nguyen Van A"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void testGetEmployeeById_Success() throws Exception {
        Department it = new Department(1L, "IT");
        Employee emp = new Employee(1L, "Nguyen Van A", "a@example.com", it, 15000000.0);
        when(employeeService.getEmployeeById(1L)).thenReturn(emp);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nguyen Van A"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void testGetEmployeeById_NotFound() throws Exception {
        when(employeeService.getEmployeeById(999L)).thenThrow(new ResourceNotFoundException("Employee not found with id: 999"));

        mockMvc.perform(get("/api/employees/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void testCreateEmployee_Success() throws Exception {
        EmployeeRequestDTO dto = new EmployeeRequestDTO("Le Van C", "c@example.com", 1L, 18000000.0);
        Employee created = new Employee(10L, "Le Van C", "c@example.com", new Department(1L, "IT"), 18000000.0);
        when(employeeService.createEmployee(any())).thenReturn(created);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void testCreateEmployee_ForbiddenForOrdinaryUser() throws Exception {
        EmployeeRequestDTO dto = new EmployeeRequestDTO("Le Van C", "c@example.com", 1L, 18000000.0);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void testDeleteEmployee_Success() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }
}
