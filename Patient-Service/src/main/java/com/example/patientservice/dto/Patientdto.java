package com.example.patientservice.dto;
import com.example.patientservice.entity.DossierMedicale;
import com.example.patientservice.entity.ImagerieMdicale;
import com.example.patientservice.entity.Rdv;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patientdto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_patient;
    private String nom;
    private String prenom;
    @Email
    private String email;
    private String password;
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

