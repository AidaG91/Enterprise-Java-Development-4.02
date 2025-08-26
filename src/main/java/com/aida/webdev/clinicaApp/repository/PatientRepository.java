package com.aida.webdev.clinicaApp.repository;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT a FROM Patient a WHERE (a.dateOfBirth BETWEEN :startDate AND :endDate)")
    List<Patient> findPatientByDateOfBirthRange(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Patient p WHERE p.employee.department = :department")
    List<Patient> findByDoctorsDepartment(@Param("department") Department department);

    @Query("SELECT p FROM Patient p WHERE p.employee.employeeStatus = :employeeStatus")
    List<Patient> findByDoctorsStatus(@Param("employeeStatus") EmployeeStatus employeeStatus);

}
