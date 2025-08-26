package com.aida.webdev.clinicaApp.repository;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findDoctorsByEmployeeStatus(EmployeeStatus employeeStatus);

    List<Employee> findDoctorsByDepartment(Department department);

}
