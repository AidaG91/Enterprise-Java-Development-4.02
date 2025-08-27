package com.aida.webdev.clinicaApp.util;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Employee;
import com.aida.webdev.clinicaApp.model.Patient;
import com.aida.webdev.clinicaApp.repository.EmployeeRepository;
import com.aida.webdev.clinicaApp.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor

public class DataInitializer implements CommandLineRunner {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public void run(String... args) {

        // EMPLOYEES = DOCTORS

        Employee doctor1 = Employee.builder()
                .id(356712L)
                .department(Department.CARDIOLOGY)
                .name("Alonso Flores")
                .employeeStatus(EmployeeStatus.ON_CALL)
                .build();

        Employee doctor2 = Employee.builder()
                .id(564134L)
                .department(Department.IMMUNOLOGY)
                .name("Sam Ortega")
                .employeeStatus(EmployeeStatus.ON)
                .build();

        Employee doctor3 = Employee.builder()
                .id(761527L)
                .department(Department.CARDIOLOGY)
                .name("German Ruiz")
                .employeeStatus(EmployeeStatus.OFF)
                .build();

        Employee doctor4 = Employee.builder()
                .id(166552L)
                .department(Department.PULMONARY)
                .name("Maria Lin")
                .employeeStatus(EmployeeStatus.ON)
                .build();

        Employee doctor5 = Employee.builder()
                .id(156545L)
                .department(Department.ORTHOPAEDIC)
                .name("Paolo Rodriguez")
                .employeeStatus(EmployeeStatus.ON_CALL)
                .build();

        Employee doctor6 = Employee.builder()
                .id(172456L)
                .department(Department.PSYCHIATRIC)
                .name("John Paul Armes")
                .employeeStatus(EmployeeStatus.OFF)
                .build();

        employeeRepository.saveAll(List.of(doctor1, doctor2, doctor3, doctor4, doctor5, doctor6));

        // PATIENTS

        Patient patient1 = Patient.builder()
                .name("Jaime Jordan")
                .dateOfBirth(LocalDate.of(1984, 3, 2))
                .employee(doctor2)
                .build();

        Patient patient2 = Patient.builder()
                .name("Marian Garcia")
                .dateOfBirth(LocalDate.of(1972, 1, 12))
                .employee(doctor2)
                .build();

        Patient patient3 = Patient.builder()
                .name("Julia Dusterdieck")
                .dateOfBirth(LocalDate.of(1954, 6,11))
                .employee(doctor1)
                .build();

        Patient patient4 = Patient.builder()
                .name("Steve McDuck")
                .dateOfBirth(LocalDate.of(1931, 11, 10))
                .employee(doctor3)
                .build();

        Patient patient5 = Patient.builder()
                .name("Marian Garcia")
                .dateOfBirth(LocalDate.of(1999,2,15))
                .employee(doctor6)
                .build();

        patientRepository.saveAll(List.of(patient1, patient2, patient3, patient4, patient5));

        System.out.println("+++++++++++++++++++++++++++++++++++ DATOS CARGADOS");
    }

}
