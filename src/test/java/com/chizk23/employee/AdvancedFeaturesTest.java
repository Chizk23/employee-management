package com.chizk23.employee;

import com.chizk23.employee.scheduler.SystemMonitorScheduler;
import com.chizk23.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdvancedFeaturesTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SystemMonitorScheduler scheduler;

    @Test
    void testCacheManagerAndReportCount() {
        assertNotNull(cacheManager);
        long count1 = employeeService.countTotalEmployeesReport();
        long count2 = employeeService.countTotalEmployeesReport();
        assertTrue(count1 >= 0);
        assertTrue(count2 >= 0);
        assertNotNull(cacheManager.getCache("employeeCountReport"));
    }

    @Test
    void testSchedulerIsInstantiated() {
        assertNotNull(scheduler);
        scheduler.logSystemStatus();
    }
}
