package com.onegateafrica.entity;


import com.onegateafrica.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column( nullable = false)
  @Length(max = 30,min = 3)
  private String nom;
  @DateTimeFormat
  private Timestamp datePromotion;

  @Transient
  private List<UsersDto> usersDtos;

  public Grade(@Length(max = 30, min = 3) String nom, Timestamp datePromotion) {
    this.nom = nom;
    this.datePromotion = datePromotion;
  }
}
