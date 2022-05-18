package com.onegateafrica.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {

  @Column( nullable = false)
  @Length(max = 30,min = 3)
  private String nom;
  @DateTimeFormat
  private Timestamp datePromotion;
  private List<UsersDto> usersDtos;
}
