package com.capgemini.wsb.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.capgemini.wsb.persistence.entity.PatientEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    @Query("SELECT p FROM PatientEntity p LEFT JOIN FETCH p.visits v LEFT JOIN FETCH v.doctor WHERE p.id = :id")
    Optional<PatientEntity> findPatientWithVisitsById(@Param("id") Long id);
    @Query("SELECT p FROM PatientEntity p JOIN p.visits v GROUP BY p HAVING COUNT(v) > :visitCount")
    List<PatientEntity> findPatientsWithMoreThanXVisits(@Param("visitCount") Long visitCount);
    List<PatientEntity> findByLastName(String lastName);
}

