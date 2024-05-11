package com.capgemini.wsb.service;

import com.capgemini.wsb.persistence.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
    boolean existsByPatientId(Long patientId);

    List<VisitEntity> findByPatientId(Long patientId);
}
