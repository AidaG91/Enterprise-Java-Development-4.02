package com.aida.webdev.clinicaApp.service;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Employee;
import com.aida.webdev.clinicaApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllDoctors() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getDoctorById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findDoctorsByEmployeeStatus(EmployeeStatus employeeStatus) {
        if (employeeStatus == null) {
            throw new IllegalArgumentException("El estado del empleado no puede ser nulo.");
        }
        return employeeRepository.findDoctorsByEmployeeStatus(employeeStatus);
    }

    public List<Employee> findDoctorsByDepartment(Department department) {
        return employeeRepository.findDoctorsByDepartment(department);
    }
}
