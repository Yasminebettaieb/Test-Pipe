package com.example.bloc.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class BlocBuilding {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id_BlocBuilding;
        private Character name;
        @OneToMany
        List<Bloc> room;

    }










