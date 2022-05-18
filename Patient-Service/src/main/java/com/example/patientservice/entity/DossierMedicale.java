package com.example.patientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DossierMedicale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_dossier;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "dossierMedicale")
    private Patient patient;
}
