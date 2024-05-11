package com.capgemini.wsb;

import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.service.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryDAOTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void whenFindByLastName_thenReturnPatients() {
        // given
        PatientEntity johnDoe = new PatientEntity();
        johnDoe.setFirstName("John");
        johnDoe.setLastName("Doe");
        johnDoe.setDateOfBirth(LocalDate.now());
        johnDoe.setTelephoneNumber("1234567890");
        johnDoe.setEmail("john.doe@example.com");
        johnDoe.setPatientNumber("PD1234");
        johnDoe.setRegistrationDate(LocalDate.now());
        entityManager.persist(johnDoe);
        entityManager.flush();

        // when
        List<PatientEntity> foundPatients = patientRepository.findByLastName("Doe");

        // then
        assertThat(foundPatients.size()).isEqualTo(1);
        assertThat(foundPatients.get(0).getLastName()).isEqualTo(johnDoe.getLastName());
    }
}
