package com.capgemini.wsb;

import com.capgemini.wsb.dto.PatientTO;
import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.Specialization;
import com.capgemini.wsb.service.DoctorRepository;
import com.capgemini.wsb.service.PatientRepository;
import com.capgemini.wsb.service.PatientService;
import com.capgemini.wsb.service.VisitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest
{
    @Autowired
    private PatientDao patientDao;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    @Transactional
    public void testDeletePatientCascadesVisitsNotDoctors() {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setDoctorNumber("DEF456");
        doctor.setFirstName("Simon");
        doctor.setLastName("Simonowski");
        doctor.setSpecialization(Specialization.valueOf("SURGEON"));
        doctor.setTelephoneNumber("123123123");
        doctor.setEmail("johndoe@example.com");
        doctor = doctorRepository.save(doctor);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDateOfBirth(LocalDate.now());
        patient.setTelephoneNumber("1234567890");
        patient.setEmail("john.doe@example.com");
        patient.setPatientNumber("PD1234");
        patient.setRegistrationDate(LocalDate.now());
        patient = patientRepository.save(patient);

        VisitEntity visit = new VisitEntity();
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setDescription("Annual Checkup");
        visit.setTime(LocalDateTime.now().plusDays(10));
        visit = visitRepository.save(visit);

        // Ensure all entities are correctly saved
        assertNotNull(patientRepository.findById(patient.getId()));
        assertNotNull(visitRepository.findById(visit.getId()));
        assertNotNull(doctorRepository.findById(doctor.getId()));

        // Perform test
        patientService.deletePatient(patient.getId());

        // Verify outcomes
        assertFalse(patientRepository.existsById(patient.getId()));
        assertFalse(visitRepository.existsByPatientId(patient.getId()));
        assertTrue(doctorRepository.existsById(doctor.getId()));
    }

    @Test
    public void testFindPatientByIdIncludesVisitsWithDoctorAndPatientInfo() {
        Long patientId = 1L;  // Założenie, że taki pacjent istnieje w bazie danych
        PatientTO patientTO = patientService.findPatientById(patientId);

        assertNotNull(patientTO);
        assertEquals(patientId, patientTO.getId());
        assertFalse(patientTO.getVisits().isEmpty());

        patientTO.getVisits().forEach(visit -> {
            assertNotNull(visit.getDoctor());
            assertEquals(patientId, visit.getId());
        });

        // Sprawdzenie poprawności dodatkowego pola, np. registrationDate
        assertEquals(LocalDate.of(2024, 4, 8), patientTO.getRegistrationDate());  // Przykład, zależy od wartości w bazie
    }

}
