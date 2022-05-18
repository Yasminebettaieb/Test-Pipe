package com.example.patientservice.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
@Jacksonized
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_patient;
    private String nom;
    private String prenom;
    private String image;
    @Email
    private String email;
    private String cin;
    private String telephone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp dateNaissance;
    private String adresse;
    private String gender;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dossier", referencedColumnName = "id_dossier")
    private DossierMedicale dossierMedicale;
    @OneToMany()
    private List<Rdv> rdvs;
    @OneToMany()
    private List<ImagerieMdicale> imageries;

}

