package com.onegateafrica.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onegateafrica.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploiDeTemps implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  @Transient
  UsersDto usersDtos;


}
