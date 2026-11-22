package com.chizk23.employee;

import com.chizk23.employee.config.AppConfig;
import com.chizk23.employee.controller.UtilityController;
import com.chizk23.employee.service.UtilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UtilityController.class)
@Import({AppConfig.class, UtilityService.class})
public class UtilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGenerateCodeEndpoint() throws Exception {
        mockMvc.perform(get("/api/utilities/generate-code").param("id", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeCode").value("EMP-0005"));
    }

    @Test
    void testDemoBeanEndpoint() throws Exception {
        mockMvc.perform(get("/api/utilities/demo-bean"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }
}
