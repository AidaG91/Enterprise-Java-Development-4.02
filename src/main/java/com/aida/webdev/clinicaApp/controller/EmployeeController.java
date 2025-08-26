package com.aida.webdev.clinicaApp.controller;

import com.aida.webdev.clinicaApp.model.Employee;
import com.aida.webdev.clinicaApp.model.Patient;
import com.aida.webdev.clinicaApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    public List<Employee> getAllDoctors() {
        return  employeeService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getDoctorById(@PathVariable Long id) {
        Optional<Employee> doctor = employeeService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
