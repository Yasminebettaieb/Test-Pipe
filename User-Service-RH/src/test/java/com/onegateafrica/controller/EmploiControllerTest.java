package com.onegateafrica.controller;

import com.onegateafrica.dto.EmploiDto;
import com.onegateafrica.entity.EmploiDeTemps;
import com.onegateafrica.repository.EmploiRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmploiControllerTest {

    private final EmploiRepository emploiRepository;
    private final ModelMapper mapper;
    @Autowired
    EmploiControllerTest(EmploiRepository emploiRepository,ModelMapper mapper){
        this.emploiRepository=emploiRepository;
        this.mapper=mapper;
    }

    @Test
    void findById() {
        emploiRepository.findById(3L);
    }

    @Test
    void findAll() {
      emploiRepository.findAll();
    }

    @Test
    void create() {
        EmploiDto emp = new EmploiDto();
        emp.setDateDebut(new Timestamp(System.currentTimeMillis()));
        emp.setDateFin(new Timestamp(System.currentTimeMillis()));
        emp.setNombreHeures(9);
        emp.setHeureSupplementaire(5);
        emploiRepository.save(mapper.map(emp, EmploiDeTemps.class));

    }

    @Test
    void update() {
        EmploiDto emp = new EmploiDto();
        emp.setId(15L);
        emp.setDateDebut(new Timestamp(System.currentTimeMillis()));
        emp.setDateFin(new Timestamp(System.currentTimeMillis()));
        emp.setNombreHeures(10);
        emp.setHeureSupplementaire(7);
        emploiRepository.save(mapper.map(emp,EmploiDeTemps.class));

    }

    @Test
    void delete() {
        emploiRepository.deleteById(15L);
    }
}