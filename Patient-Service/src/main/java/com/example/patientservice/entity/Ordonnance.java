package com.example.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ordonnance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Ordonnance;
    private  long consultation;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordonnance_con")
    private Consultation Consultation_ordonnance;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Medicament> medicamentList;



}
