package com.chizk23.employee.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemMonitorScheduler {

    private static final Logger log = LoggerFactory.getLogger(SystemMonitorScheduler.class);

    @Scheduled(fixedRate = 30000)
    public void logSystemStatus() {
        log.info("System running");
    }
}
