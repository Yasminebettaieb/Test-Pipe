package com.example.patientservice.repository;

import com.example.patientservice.entity.Patient;
import com.example.patientservice.entity.Rdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface RdvRepository extends JpaRepository<Rdv,Long> {
    @Query("from Rdv s where DATE(s.date) = :date")
    List<Rdv> findRdvByAndDate(Date date);
    List<Rdv> findRdvByPatient(Patient patient);
    List<Rdv> findRdvByIdMedecin(Long id);
}
