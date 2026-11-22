package com.chizk23.employee.controller;

import com.chizk23.employee.dto.SampleDTO;
import com.chizk23.employee.service.UtilityService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/utilities")
public class UtilityController {

    private final UtilityService utilityService;
    private final ModelMapper modelMapper;

    // Constructor Injection (IoC & DI)
    public UtilityController(UtilityService utilityService, ModelMapper modelMapper) {
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/generate-code")
    public ResponseEntity<Map<String, Object>> generateCode(@RequestParam(defaultValue = "1") Long id) {
        String code = utilityService.generateEmployeeCode(id);
        Map<String, Object> res = new HashMap<>();
        res.put("employeeId", id);
        res.put("employeeCode", code);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/format-name")
    public ResponseEntity<Map<String, String>> formatName(@RequestParam(defaultValue = "nguyen van a") String name) {
        String formatted = utilityService.formatFullName(name);
        Map<String, String> res = new HashMap<>();
        res.put("original", name);
        res.put("formatted", formatted);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/demo-bean")
    public ResponseEntity<Map<String, Object>> demoModelMapperBean() {
        SampleDTO source = new SampleDTO("Sample Title", null);
        SampleDTO destination = new SampleDTO();
        modelMapper.map(source, destination);

        Map<String, Object> res = new HashMap<>();
        res.put("message", "ModelMapper bean injected and working via IoC!");
        res.put("mappedObject", destination);
        return ResponseEntity.ok(res);
    }
}
