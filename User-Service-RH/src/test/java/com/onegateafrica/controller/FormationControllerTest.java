package com.onegateafrica.controller;

import com.onegateafrica.dto.FormationDto;
import com.onegateafrica.entity.Formation;
import com.onegateafrica.repository.FormationRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class FormationControllerTest {
@Autowired
 private  FormationRepository formationRepository  ;
    private  ModelMapper mapper;

    @Test
    void create() {
       FormationDto formation=new FormationDto();
       formation.setNom("test");
       formation.setEtatFormation(false);
       formation.setDateDebut(Timestamp.valueOf(LocalDateTime.now()));
       formation.setDateFin(Timestamp.valueOf(LocalDateTime.now()));
       formation.setFormerName("ramzi");
       formation.setNbPlaceDisponible(50);
       formation.setDomaine("test");
       formationRepository.save(mapper.map(formation, Formation.class));

    }

    @Test
    void delete() {
        formationRepository.deleteById(2l);

    }

    @Test
    void findall() {
        formationRepository.findAll();
    }

    @Test
    void getone() {
      formationRepository.getById(2l);
    }

    @Test
    void update() {
        FormationDto formation=new FormationDto();
        formation.setNom("test");
        formation.setEtatFormation(false);
        formation.setDateDebut(Timestamp.valueOf(LocalDateTime.now()));
        formation.setDateFin(Timestamp.valueOf(LocalDateTime.now()));
        formation.setFormerName("ramzi");
        formation.setNbPlaceDisponible(50);
        formation.setDomaine("test");
        formationRepository.save(mapper.map(formation, Formation.class));
    }

}

