package com.example.patientservice.entity;


import com.example.patientservice.dto.UsersDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Consultation;
    @Transient
    private UsersDto medecin;
    //id patient
    private long id_patient;
    private long idMedecin;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp date;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateCreation;
    @JsonFormat(pattern = "HH:mm")
    private Timestamp heure;
    private String diagnostique ;
    @OneToOne(cascade = CascadeType.ALL)
    private Ordonnance ordonnance;
}

