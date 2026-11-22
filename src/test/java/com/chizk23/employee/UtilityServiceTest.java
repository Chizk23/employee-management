package com.chizk23.employee;

import com.chizk23.employee.service.UtilityService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilityServiceTest {

    private final UtilityService utilityService = new UtilityService();

    @Test
    void testGenerateEmployeeCode() {
        assertEquals("EMP-0001", utilityService.generateEmployeeCode(1L));
        assertEquals("EMP-0042", utilityService.generateEmployeeCode(42L));
    }

    @Test
    void testFormatFullName() {
        assertEquals("Nguyen Van A", utilityService.formatFullName("  nguyen   van   a  "));
    }
}
