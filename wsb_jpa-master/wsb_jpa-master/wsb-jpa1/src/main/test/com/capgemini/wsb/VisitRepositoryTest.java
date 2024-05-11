package com.capgemini.wsb;

import com.capgemini.wsb.dto.VisitTO;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.service.VisitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Test
    public void whenFindVisitsByPatientId_thenReturnVisitsList() {
        // given
        Long patientId = 1L;  // Assuming this patient is in the database and has visits

        // when
        List<VisitEntity> visits = visitRepository.findByPatientId(patientId);

        // then
        assertFalse(visits.isEmpty());
        assertTrue(visits.stream().allMatch(visit -> visit.getId().equals(patientId)));
    }
}
