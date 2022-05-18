package com.onegateafrica.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CongeDto {
  @JsonFormat(pattern="yyyy-MM-dd")
  private Timestamp dateDebut;
  @JsonFormat(pattern="yyyy-MM-dd")
  private Timestamp dateFin;
  private String typeConge;
  private String raison;
  private Boolean confirmationChefService;
  private Boolean confirmationRh;
  private long idUser;

}
