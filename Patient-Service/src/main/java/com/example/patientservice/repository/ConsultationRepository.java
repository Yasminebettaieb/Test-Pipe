package com.example.patientservice.repository;
import com.example.patientservice.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
    List<Consultation> getAllByIdMedecin(long id);

}
