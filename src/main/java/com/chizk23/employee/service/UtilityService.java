package com.chizk23.employee.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class UtilityService {

    public String generateEmployeeCode(Long id) {
        if (id == null || id <= 0) {
            id = 1L;
        }
        return String.format("EMP-%04d", id);
    }

    public String formatFullName(String rawName) {
        if (rawName == null || rawName.trim().isEmpty()) {
            return "";
        }
        return Arrays.stream(rawName.trim().split("\\s+"))
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
