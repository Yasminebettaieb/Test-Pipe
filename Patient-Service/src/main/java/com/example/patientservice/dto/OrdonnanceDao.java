package com.example.patientservice.dto;

import com.example.patientservice.entity.Consultation;
import com.example.patientservice.entity.Ordonnance;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrdonnanceDao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Ordonnance;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordonnance_con")
    private Consultation Consultation_ordonnance;
    private  long consultation;

}
