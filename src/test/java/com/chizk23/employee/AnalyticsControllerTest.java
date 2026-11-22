package com.chizk23.employee;

import com.chizk23.employee.controller.AnalyticsController;
import com.chizk23.employee.dto.DepartmentStatisticsDTO;
import com.chizk23.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnalyticsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testGetDepartmentStatistics() throws Exception {
        when(employeeService.getDepartmentStatistics()).thenReturn(List.of(
                new DepartmentStatisticsDTO("IT", 5L, 20000000.0)
        ));

        mockMvc.perform(get("/api/analytics/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].departmentName").value("IT"))
                .andExpect(jsonPath("$[0].employeeCount").value(5));
    }

    @Test
    void testGetAnalyticsSummary() throws Exception {
        when(employeeService.getAnalyticsSummary()).thenReturn(Map.of("totalEmployees", 10L));

        mockMvc.perform(get("/api/analytics/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalEmployees").value(10));
    }
}
