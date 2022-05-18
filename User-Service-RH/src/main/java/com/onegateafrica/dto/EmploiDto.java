package com.onegateafrica.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmploiDto {
  private long id;
  private int nombreHeures;
  private int heureSupplementaire;
  @JsonFormat(pattern="yyyy-MM-dd")
  private Timestamp dateDebut;
  @JsonFormat(pattern="yyyy-MM-dd")
  private Timestamp dateFin;
  @JsonFormat(pattern = "HH:mm")
  private Timestamp HeureDebut;
  @JsonFormat(pattern = "HH:mm")
  private Timestamp HeureFin;
  private long idUser;
}
