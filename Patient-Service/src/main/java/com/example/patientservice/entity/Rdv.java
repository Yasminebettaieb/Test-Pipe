package com.example.patientservice.entity;

import com.example.patientservice.dto.Patientdto;
import com.example.patientservice.dto.UsersDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rdv implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Rdv;
    //id medecin
    private long idMedecin;
    @Transient
    private UsersDto medecin;
    //id patient
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp date;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateCreation;
    @JsonFormat(pattern = "HH:mm")
    private Timestamp heure;
}
