package com.aida.webdev.clinicaApp.controller;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Employee;
import com.aida.webdev.clinicaApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllDoctors() {
        return  employeeService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getDoctorById(@PathVariable Long id) {
        Optional<Employee> doctor = employeeService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find/by-status")
    public ResponseEntity<List<Employee>> findDoctorsByEmployeeStatus(@RequestParam EmployeeStatus employeeStatus) {
        List<Employee> doctors = employeeService.findDoctorsByEmployeeStatus(employeeStatus);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/find/by-department")
    public ResponseEntity<List<Employee>> findDoctorsByDepartment(@RequestParam Department department) {
        List<Employee> doctors = employeeService.findDoctorsByDepartment(department);
        return ResponseEntity.ok(doctors);
    }
}
