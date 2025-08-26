package com.aida.webdev.clinicaApp.controller;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Patient;
import com.aida.webdev.clinicaApp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")

public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find/by-birthdate-range")
    public List<Patient> findPatientByDateOfBirthRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
            ){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return patientService.findPatientByDateOfBirthRange(start, end);
    }

    @GetMapping("/find/by-doctors-department")
    public ResponseEntity<List<Patient>> findPatientByDoctorsDepartment(@RequestParam Department department) {
        List<Patient> patients = patientService.findPatientByDoctorsDepartment(department);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/find/by-doctors-status")
    public ResponseEntity<List<Patient>> findPatientByDoctorsStatus(@RequestParam EmployeeStatus employeeStatus) {
        List<Patient> patients = patientService.findPatientByDoctorsStatus(employeeStatus);
        return ResponseEntity.ok(patients);
    }
}
