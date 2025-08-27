package com.aida.webdev.clinicaApp.model;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Employee {
    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Department department;

    private EmployeeStatus employeeStatus;

    @OneToMany(mappedBy = "employee")
    private List<Patient> patients;
}
