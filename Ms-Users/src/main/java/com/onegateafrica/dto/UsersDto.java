package com.onegateafrica.dto;


import com.onegateafrica.entity.Roles;
import com.onegateafrica.entity.Spécialité;
import com.onegateafrica.entity.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDto implements Serializable {
  private long id;
  private String nom;
  private String prenom;
  @Email
  private String email;


  private String password;
  private String cin;
  private String phoneNumber;
  private Boolean emailConfirmed;
  private Timestamp dateNaissance;
  private String adresse;
  private String gender;
  private Timestamp dateEmploi;
  private String matricule;
  private Boolean etatCompte;
  private Boolean acceptPromotion;
  @Enumerated(EnumType.STRING)
  private Roles role;


  @Enumerated(EnumType.STRING)
  private Spécialité spécialité;

  private List<UserGrade>UserGrade;
  private List<FormationDto> formationList;
  private long idEmploi;
  private List<CongeDto> congeList;

}

