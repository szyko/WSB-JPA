package com.capgemini.wsb.service;

import com.capgemini.wsb.dto.PatientTO;
import com.capgemini.wsb.mapper.PatientMapper;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.rest.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService{

    @PersistenceContext
    private EntityManager em;

    public PatientEntity getPatientWithVisits(Long patientId) {
        TypedQuery<PatientEntity> query = em.createQuery(
                "SELECT p FROM PatientEntity p JOIN FETCH p.visits WHERE p.id = :patientId",
                PatientEntity.class
        );
        query.setParameter("patientId", patientId);
        return query.getSingleResult();
    }
    @Autowired
    private VisitRepository visitRepository;
    @Transactional
    public void deletePatient(Long patientId) {
        // Najpierw znajdź i usuń wszystkie wizyty związane z pacjentem
        List<VisitEntity> visits = visitRepository.findByPatientId(patientId);
        if (!visits.isEmpty()) {
            visitRepository.deleteAll(visits);  // Usunięcie wszystkich wizyt
        }

        // Następnie usuń pacjenta
        patientRepository.deleteById(patientId);
    }


    public PatientTO findPatientById(Long id) {
        return patientRepository.findPatientWithVisitsById(id)
                .map(PatientMapper::mapToTO)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
    private final PatientRepository patientRepository;


    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientTO> findAllPatientsWithVisits() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }
    public List<PatientTO> findAll() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }

    public Optional<PatientTO> findById(Long id) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        if (patientEntity.isPresent()) {
            return Optional.of(PatientMapper.mapToTO(patientEntity.get()));
        } else {
            return Optional.empty();
        }
    }

}
