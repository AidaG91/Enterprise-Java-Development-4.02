package com.aida.webdev.clinicaApp.model;

import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    @Id
    private Long id;

    private String name;
    private String department;
    private EmployeeStatus employeeStatus;

    @OneToMany(mappedBy = "employee")
    private List<Patient> patients;
}
