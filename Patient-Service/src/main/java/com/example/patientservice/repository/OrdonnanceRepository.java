package com.example.patientservice.repository;

import com.example.patientservice.entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdonnanceRepository  extends JpaRepository<Ordonnance,Long> {
    List<Ordonnance> findAllByConsultation(long consultation );
}
