package com.example.patientservice.dto;

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
public class RdvDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Rdv;
    //id medecin
    private long idMedecin;
    //id patient
    //private long idPatient;
    @Transient
    private Patient patient;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp date;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateCreation;
    @JsonFormat(pattern = "HH:mm")
    private Timestamp heure;

}
