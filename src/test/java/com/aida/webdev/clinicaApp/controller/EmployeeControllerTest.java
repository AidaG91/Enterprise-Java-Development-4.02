package com.aida.webdev.clinicaApp.controller;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Employee;
import com.aida.webdev.clinicaApp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    EmployeeService employeeService;

    // ------- GET /api/employees -------
    @Test
    @DisplayName("GET /api/employees -> 200 OK con lista de empleados")
    void getAllDoctors_ok() throws Exception {
        var e1 = employee(1L, "Ana", Department.CARDIOLOGY, EmployeeStatus.ON);
        var e2 = employee(2L, "Luis", Department.ORTHOPAEDIC, EmployeeStatus.OFF);
        when(employeeService.getAllDoctors()).thenReturn(List.of(e1, e2));

        mvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].name").value("Luis"));

        verify(employeeService).getAllDoctors();
    }

    // ------- GET /api/employees/{id} -------
    @Test
    @DisplayName("GET /api/employees/{id} -> 200 OK cuando existe")
    void getDoctorById_ok() throws Exception {
        var emp = employee(10L, "Ana", Department.CARDIOLOGY, EmployeeStatus.ON);
        when(employeeService.getDoctorById(10L)).thenReturn(Optional.of(emp));

        mvc.perform(get("/api/employees/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Ana"));

        verify(employeeService).getDoctorById(10L);
    }

    @Test
    @DisplayName("GET /api/employees/{id} -> 404 Not Found cuando no existe")
    void getDoctorById_notFound() throws Exception {
        when(employeeService.getDoctorById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/employees/{id}", 99))
                .andExpect(status().isNotFound());

        verify(employeeService).getDoctorById(99L);
    }

    // ------- GET /api/employees/find/by-status -------
    @Test
    @DisplayName("GET /api/employees/find/by-status -> 200 OK con lista filtrada")
    void findDoctorsByStatus_ok() throws Exception {
        var e1 = employee(3L, "Clara", Department.IMMUNOLOGY, EmployeeStatus.ON);
        when(employeeService.findDoctorsByEmployeeStatus(EmployeeStatus.ON)).thenReturn(List.of(e1));

        mvc.perform(get("/api/employees/find/by-status")
                        .param("employeeStatus", "ON"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Clara"));

        verify(employeeService).findDoctorsByEmployeeStatus(EmployeeStatus.ON);
    }

    @Test
    @DisplayName("GET /api/employees/find/by-status -> 400 Bad Request si el parámetro es inválido")
    void findDoctorsByStatus_badRequest() throws Exception {
        mvc.perform(get("/api/employees/find/by-status")
                        .param("employeeStatus", "UNKNOWN"))
                .andExpect(status().isBadRequest());
    }

    // ------- GET /api/employees/find/by-department -------
    @Test
    @DisplayName("GET /api/employees/find/by-department -> 200 OK con lista filtrada")
    void findDoctorsByDepartment_ok() throws Exception {
        var e1 = employee(4L, "Mario", Department.PULMONARY, EmployeeStatus.ON);
        when(employeeService.findDoctorsByDepartment(Department.PULMONARY)).thenReturn(List.of(e1));

        mvc.perform(get("/api/employees/find/by-department")
                        .param("department", "PULMONARY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Mario"));

        verify(employeeService).findDoctorsByDepartment(Department.PULMONARY);
    }

    @Test
    @DisplayName("GET /api/employees/find/by-department -> 400 Bad Request si el parámetro es inválido")
    void findDoctorsByDepartment_badRequest() throws Exception {
        mvc.perform(get("/api/employees/find/by-department")
                        .param("department", "UNKNOWN"))
                .andExpect(status().isBadRequest());
    }

    // -------- Helpers --------
    private static Employee employee(Long id, String name, Department dept, EmployeeStatus status) {
        var e = new Employee();
        e.setId(id);
        e.setName(name);
        e.setDepartment(dept);
        e.setEmployeeStatus(status);
        return e;
    }
}