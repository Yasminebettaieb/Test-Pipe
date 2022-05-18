package com.onegateafrica.dto;

import java.sql.Timestamp;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormationDto {

  private String nom;
  private Timestamp dateDebut;
  private Timestamp dateFin;
  private String domaine;
  private int nbPlaceDisponible;
  private String nomSalle;
  private Boolean etatFormation;
  private List<UsersDto> useresDtos;
}
