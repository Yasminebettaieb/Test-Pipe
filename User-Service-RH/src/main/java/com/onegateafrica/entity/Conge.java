package com.onegateafrica.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.sql.Timestamp;

import com.onegateafrica.dto.UsersDto;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateDebut;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateFin;
    private String typeConge;
    private String raison;
    private Boolean confirmationChefService;
    private Boolean confirmationRh;
    private long idUser;
    @Transient
    private UsersDto usersDto;
    public Conge(Timestamp dateDebut, Timestamp dateFin, String typeConge, String raison) {
        this.dateDebut= dateDebut;
        this.dateFin= dateFin;
        this.typeConge= typeConge;
        this.raison= raison;
    }
}
