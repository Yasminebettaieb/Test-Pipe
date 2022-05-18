package com.onegateafrica.dto;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmploiDto implements Serializable {

  private long id;
  private int nombreHeures;
  private int heureSupplementaire;
  private Timestamp dateDebut;
  private Timestamp dateFin;
  private Timestamp HeureDebut;
  private Timestamp HeureFin;
  private long idUser;

}
