package com.aida.webdev.clinicaApp.service;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Patient;
import com.aida.webdev.clinicaApp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> findPatientByDateOfBirthRange(LocalDate startDate, LocalDate endDate) {
       if (startDate.isAfter(endDate)) {
           throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
       }
        return patientRepository.findPatientByDateOfBirthRange(startDate, endDate);
    }

    public List<Patient> findPatientByDoctorsDepartment(Department department) {
        return patientRepository.findByDoctorsDepartment(department);
    }

    public List<Patient> findPatientByDoctorsStatus(EmployeeStatus employeeStatus) {
        return patientRepository.findByDoctorsStatus(employeeStatus);
    }
}
