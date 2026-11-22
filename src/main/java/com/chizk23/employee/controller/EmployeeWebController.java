package com.chizk23.employee.controller;

import com.chizk23.employee.dto.EmployeeRequestDTO;
import com.chizk23.employee.repository.DepartmentRepository;
import com.chizk23.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    public EmployeeWebController(EmployeeService employeeService, DepartmentRepository departmentRepository) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/list")
    public String showEmployeeList(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new EmployeeRequestDTO());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employees/add";
    }

    @PostMapping("/add")
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeRequestDTO employeeDTO,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "employees/add";
        }
        employeeService.createEmployee(employeeDTO);
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("results", employeeService.searchEmployees(keyword));
        } else {
            model.addAttribute("results", employeeService.getAllEmployees());
        }
        return "employees/search";
    }
}
