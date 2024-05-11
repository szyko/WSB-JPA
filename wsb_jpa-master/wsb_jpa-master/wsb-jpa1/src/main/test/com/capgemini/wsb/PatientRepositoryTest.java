package com.capgemini.wsb;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.Specialization;

import com.capgemini.wsb.service.DoctorRepository;
import com.capgemini.wsb.service.PatientRepository;
import com.capgemini.wsb.service.PatientService;
import com.capgemini.wsb.service.VisitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientDao patientDao;

    @Autowired
    private PatientService patientService;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        // Utwórz pacjentów i dodaj im wizyty w sposób, który zagwarantuje, że niektórzy z nich będą mieli więcej niż X wizyt
        // Oto przykładowe dane testowe, które możemy użyć:
        PatientEntity johnDoe = new PatientEntity();
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");
        johnDoe.setDateOfBirth(LocalDate.now());
        johnDoe.setTelephoneNumber("1234567890");
        johnDoe.setEmail("john.doe@example.com");
        johnDoe.setPatientNumber("PD1234");
        johnDoe.setRegistrationDate(LocalDate.now());
        johnDoe = patientRepository.save(johnDoe);

        PatientEntity janeSmith = new PatientEntity();
        janeSmith.setFirstName("Jane");
        janeSmith.setLastName("Smith");
        janeSmith.setDateOfBirth(LocalDate.now());
        janeSmith.setTelephoneNumber("1234567891");
        janeSmith.setEmail("jane.smith@example.com");
        janeSmith.setPatientNumber("PD1235");
        janeSmith.setRegistrationDate(LocalDate.now());
        janeSmith = patientRepository.save(janeSmith);

        PatientEntity aliceJohnson = new PatientEntity();
        aliceJohnson.setFirstName("Alice");
        aliceJohnson.setLastName("Johnson");
        aliceJohnson.setDateOfBirth(LocalDate.now());
        aliceJohnson.setTelephoneNumber("1234567892");
        aliceJohnson.setEmail("alice.johnson@example.com");
        aliceJohnson.setPatientNumber("PD1236");
        aliceJohnson.setRegistrationDate(LocalDate.now());
        aliceJohnson = patientRepository.save(aliceJohnson);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setDoctorNumber("DEF456");
        doctor.setFirstName("Simon");
        doctor.setLastName("Simonowski");
        doctor.setSpecialization(Specialization.valueOf("SURGEON"));
        doctor.setTelephoneNumber("123123123");
        doctor.setEmail("simonsimonowski@example.com");
        doctor = doctorRepository.save(doctor);

        VisitEntity visit = new VisitEntity();
        visit.setDoctor(doctor);
        visit.setPatient(johnDoe);
        visit.setDescription("Annual Checkup");
        visit.setTime(LocalDateTime.now().plusDays(10));
        visit = visitRepository.save(visit);

        VisitEntity visit2 = new VisitEntity();
        visit2.setDoctor(doctor);
        visit2.setPatient(johnDoe);
        visit2.setDescription("Annual Checkup");
        visit2.setTime(LocalDateTime.now().plusDays(10));
        visit2 = visitRepository.save(visit2);

        VisitEntity visit3 = new VisitEntity();
        visit3.setDoctor(doctor);
        visit3.setPatient(johnDoe);
        visit3.setDescription("Annual Checkup");
        visit3.setTime(LocalDateTime.now().plusDays(10));
        visit3 = visitRepository.save(visit3);

        VisitEntity visit4 = new VisitEntity();
        visit4.setDoctor(doctor);
        visit4.setPatient(johnDoe);
        visit4.setDescription("Annual Checkup");
        visit4.setTime(LocalDateTime.now().plusDays(10));
        visit4 = visitRepository.save(visit4);


        // Wyszukaj pacjentów, którzy mieli więcej niż X wizyt (np. X = 3)
        int numberOfVisitsThreshold = 3;
        List<PatientEntity> patientsWithMoreThanXVisits = patientRepository.findPatientsWithMoreThanXVisits((long) numberOfVisitsThreshold);

        // Sprawdź czy znaleziono odpowiednią liczbę pacjentów
        assertThat(patientsWithMoreThanXVisits.size()).isEqualTo(1); // Oczekujemy tylko jednego pacjenta (johnDoe), który miał więcej niż 3 wizyty
    }

}

