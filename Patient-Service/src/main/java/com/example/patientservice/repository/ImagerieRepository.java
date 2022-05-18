package com.example.patientservice.repository;

import com.example.patientservice.entity.ImagerieMdicale;
import com.example.patientservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagerieRepository extends JpaRepository<ImagerieMdicale,Long> {
    ImagerieMdicale findImagerieMdicaleByImageId(String id);
    ImagerieMdicale findImagerieMdicaleByRapportId(String id);
    List<ImagerieMdicale> findImagerieMdicaleByPatient(Patient p);
}
