package com.aida.webdev.clinicaApp.controller;

import com.aida.webdev.clinicaApp.enums.Department;
import com.aida.webdev.clinicaApp.enums.EmployeeStatus;
import com.aida.webdev.clinicaApp.model.Patient;
import com.aida.webdev.clinicaApp.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    PatientService patientService;

    // -------- GET /api/patients --------
    @Test
    @DisplayName("GET /api/patients -> 200 OK con lista de pacientes")
    void getAllPatients_ok() throws Exception {
        var p1 = patient(1L, "Laura", LocalDate.of(1990, 5, 10));
        var p2 = patient(2L, "Carlos", LocalDate.of(1985, 3, 22));
        when(patientService.getAllPatients()).thenReturn(List.of(p1, p2));

        mvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Laura"))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(patientService).getAllPatients();
    }

    // -------- GET /api/patients/{id} --------
    @Test
    @DisplayName("GET /api/patients/{id} -> 200 OK cuando existe")
    void getPatientById_ok() throws Exception {
        var p = patient(5L, "Marta", LocalDate.of(2000, 1, 1));
        when(patientService.getPatientById(5L)).thenReturn(Optional.of(p));

        mvc.perform(get("/api/patients/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Marta"));

        verify(patientService).getPatientById(5L);
    }

    @Test
    @DisplayName("GET /api/patients/{id} -> 404 Not Found cuando no existe")
    void getPatientById_notFound() throws Exception {
        when(patientService.getPatientById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/patients/{id}", 99))
                .andExpect(status().isNotFound());

        verify(patientService).getPatientById(99L);
    }

    // -------- GET /api/patients/find/by-birthdate-range --------
    @Test
    @DisplayName("GET /api/patients/find/by-birthdate-range -> 200 OK con lista filtrada")
    void findPatientByDateOfBirthRange_ok() throws Exception {
        var p = patient(3L, "Julia", LocalDate.of(1995, 6, 15));
        when(patientService.findPatientByDateOfBirthRange(
                LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31)))
                .thenReturn(List.of(p));

        mvc.perform(get("/api/patients/find/by-birthdate-range")
                        .param("startDate", "1990-01-01")
                        .param("endDate", "2000-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Julia"));

        verify(patientService).findPatientByDateOfBirthRange(
                LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31));
    }

    // -------- GET /api/patients/find/by-doctors-department --------
    @Test
    @DisplayName("GET /api/patients/find/by-doctors-department -> 200 OK con lista filtrada")
    void findPatientByDoctorsDepartment_ok() throws Exception {
        var p = patient(4L, "Sergio", LocalDate.of(1988, 9, 9));
        when(patientService.findPatientByDoctorsDepartment(Department.PSYCHIATRIC)).thenReturn(List.of(p));

        mvc.perform(get("/api/patients/find/by-doctors-department")
                        .param("department", "PSYCHIATRIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Sergio"));

        verify(patientService).findPatientByDoctorsDepartment(Department.PSYCHIATRIC);
    }

    @Test
    @DisplayName("GET /api/patients/find/by-doctors-department -> 400 Bad Request si el parámetro es inválido")
    void findPatientByDoctorsDepartment_badRequest() throws Exception {
        mvc.perform(get("/api/patients/find/by-doctors-department")
                        .param("department", "UNKNOWN"))
                .andExpect(status().isBadRequest());
    }

    // -------- GET /api/patients/find/by-doctors-status --------
    @Test
    @DisplayName("GET /api/patients/find/by-doctors-status -> 200 OK con lista filtrada")
    void findPatientByDoctorsStatus_ok() throws Exception {
        var p = patient(6L, "Lucía", LocalDate.of(1992, 4, 4));
        when(patientService.findPatientByDoctorsStatus(EmployeeStatus.ON)).thenReturn(List.of(p));

        mvc.perform(get("/api/patients/find/by-doctors-status")
                        .param("employeeStatus", "ON"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Lucía"));

        verify(patientService).findPatientByDoctorsStatus(EmployeeStatus.ON);
    }

    @Test
    @DisplayName("GET /api/patients/find/by-doctors-status -> 400 Bad Request si el parámetro es inválido")
    void findPatientByDoctorsStatus_badRequest() throws Exception {
        mvc.perform(get("/api/patients/find/by-doctors-status")
                        .param("employeeStatus", "UNKNOWN"))
                .andExpect(status().isBadRequest());
    }

    // -------- Helpers --------
    private static Patient patient(Long id, String name, LocalDate birthDate) {
        var p = new Patient();
        p.setId(id);
        p.setName(name);
        p.setDateOfBirth(birthDate);
        return p;
    }
}
