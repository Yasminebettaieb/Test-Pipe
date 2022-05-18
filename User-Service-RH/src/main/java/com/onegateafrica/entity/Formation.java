package com.onegateafrica.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.sql.Timestamp;
import java.util.List;

import com.onegateafrica.dto.UsersDto;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private String domaine;
    private int nbPlaceDisponible;
    private String nomSalle;
    private Boolean etatFormation;
    @Transient
    private List<UsersDto> usersDtos;


}
