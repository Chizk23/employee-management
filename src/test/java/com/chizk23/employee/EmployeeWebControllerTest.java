package com.chizk23.employee;

import com.chizk23.employee.controller.EmployeeWebController;
import com.chizk23.employee.model.Department;
import com.chizk23.employee.model.Employee;
import com.chizk23.employee.repository.DepartmentRepository;
import com.chizk23.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeWebController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    void testShowEmployeeList() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of(
                new Employee(1L, "Nguyen Van A", "a@example.com", new Department(1L, "IT"), 15000000.0)
        ));

        mockMvc.perform(get("/employees/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/list"))
                .andExpect(model().attributeExists("employees"));
    }

    @Test
    void testShowAddForm() throws Exception {
        when(departmentRepository.findAll()).thenReturn(List.of(new Department(1L, "IT")));

        mockMvc.perform(get("/employees/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/add"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    void testSaveEmployee_Valid() throws Exception {
        mockMvc.perform(post("/employees/add")
                .param("name", "Nguyen Van B")
                .param("email", "vanb@example.com")
                .param("salary", "12000000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/list"));
    }

    @Test
    void testSearchEmployees() throws Exception {
        when(employeeService.searchEmployees("IT")).thenReturn(List.of());

        mockMvc.perform(get("/employees/search").param("keyword", "IT"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/search"))
                .andExpect(model().attributeExists("results"));
    }
}
