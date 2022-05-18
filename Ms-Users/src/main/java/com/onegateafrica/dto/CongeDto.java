package com.onegateafrica.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CongeDto {
  private Timestamp dateDebut;
  private Timestamp dateFin;
  private String typeConge;
  private Boolean confirmationChefService;
  private Boolean confirmationRh;
  private long idUser;
}
