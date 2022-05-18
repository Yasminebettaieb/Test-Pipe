package com.onegateafrica.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onegateafrica.dto.CongeDto;
import com.onegateafrica.dto.EmploiDto;
import com.onegateafrica.dto.FormationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String nom;
  private String prenom;
  @Email
  private String email;
  private String password;
  private String cin;
  private String phoneNumber;
  private Boolean emailConfirmed;
  @JsonFormat(pattern="yyyy-MM-dd")
  private Timestamp dateNaissance;
  private String adresse;
  private String gender;
  @JsonFormat(pattern="yyyy-MM-dd")
  private Timestamp dateEmploi;
  private String matricule;
  private Boolean etatCompte;
  private Boolean acceptPromotion;
  @Enumerated(EnumType.STRING)
  private Roles role;
  @Enumerated(EnumType.STRING)
  private Spécialité spécialité;
  @Transient
  private List<UserGrade>UserGrade;
  @Transient
  private List<FormationDto> formationList;
  private long idEmploi;
  @Transient
  private EmploiDto emploiDto;
  private long idConge;
  @Transient
  private List<CongeDto> congeDto;

}

