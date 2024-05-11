package com.capgemini.wsb;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.service.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testFindPatientsByCustomField() {
        // Szukamy pacjentów według dodanego pola (rejestracji)
        LocalDate fromDate = LocalDate.of(1980, 1, 1);
        LocalDate toDate = LocalDate.of(1990, 1, 1);
        List<PatientEntity> patients = patientDao.findByDateOfRegistrationBetween(fromDate, toDate);

        // Sprawdzamy, czy znaleźliśmy odpowiednią liczbę pacjentów
        assertThat(patients).hasSize(1);
        // Sprawdzamy, czy pacjent ma oczekiwaną wartość w dodanym polu (w data.sql jest użytkownik john doe ktory wpasuje sie w daty)
        assertThat(patients.get(0).getFirstName()).isEqualTo("Prosze o 3 :(");
        assertThat(patients.get(0).getLastName()).isEqualTo("Prosze o 3 :(");
    }
}

