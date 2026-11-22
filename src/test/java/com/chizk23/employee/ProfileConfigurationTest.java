package com.chizk23.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
public class ProfileConfigurationTest {

    @Autowired
    private Environment environment;

    @Test
    void testDevProfileActive() {
        String[] activeProfiles = environment.getActiveProfiles();
        assertNotNull(activeProfiles);
        assertEquals(1, activeProfiles.length);
        assertEquals("dev", activeProfiles[0]);
    }
}
