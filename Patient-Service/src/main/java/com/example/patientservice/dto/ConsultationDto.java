package com.example.patientservice.dto;

import com.example.patientservice.entity.Ordonnance;
import com.example.patientservice.entity.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Consultation;
    private long idMedecin;
    @Transient
    private UsersDto medecin;
    //id patient

    private long id_patient;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp date;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateCreation;
    @JsonFormat(pattern = "HH:mm")
    private Timestamp heure;
    private String diagnostique;
    @OneToOne(cascade = CascadeType.ALL)
    private Ordonnance ordonnance;

}

