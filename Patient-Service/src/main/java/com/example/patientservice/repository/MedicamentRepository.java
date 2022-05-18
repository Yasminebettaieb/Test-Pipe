package com.example.patientservice.repository;

import com.example.patientservice.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicamentRepository extends JpaRepository<Medicament,Long> {
}
