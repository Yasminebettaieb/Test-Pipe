package com.onegateafrica;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Timestamp;
import java.util.Date;

import com.onegateafrica.dto.CongeDto;
import com.onegateafrica.entity.Conge;
import com.onegateafrica.repository.CongeRepository;
import com.onegateafrica.repository.GradeRepositoty;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.onegateafrica.dto.GradeDto;
import com.onegateafrica.entity.Grade;



@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {


    private TestRestTemplate restTemplate;
    private String url="http://localhost:8080/rh-ms/api/grade";
    private String urlConge="http://localhost:8080/rh-ms/api/conge";
    private ModelMapper mapper;
    private GradeRepositoty gradeRepositoty;
    private CongeRepository congeRepository;

    @Autowired


    public DemoApplicationTests(TestRestTemplate restTemplate, ModelMapper mapper, GradeRepositoty gradeRepositoty, CongeRepository congeRepository) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.gradeRepositoty=gradeRepositoty;
        this.congeRepository=congeRepository;
    }

    @Test
    void contextLoads() {
    }


    @Test
    void updateGrade() {
        int id = 31;
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        Grade grade = restTemplate.getForObject(url + id, Grade.class);
        grade.setNom("spring test");
        grade.setDatePromotion(ts);
        restTemplate.put(url + id, grade);
        Grade updatedGrade = restTemplate.getForObject(url + id, Grade.class);
        assertNotNull(updatedGrade);
    }

    @Test
    void getAllGrade() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url ,
            HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    void deleteGrade() {
        int id = 20;
        Grade grade = restTemplate.getForObject(url + id, Grade.class);
        assertNotNull(grade);
        restTemplate.delete(url + id);
    }



    @Test
    void createProduits(){
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        Grade grade = new Grade("test create",ts);
        var p =gradeRepositoty.save(mapper.map(grade, Grade.class));
        assertNotNull(p);
    }
    @Test
    void createConge(){
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        Conge conge = new Conge(ts,ts,"paye","maladie");
        var p =congeRepository.save(conge);
        assertNotNull(p);
    }
    @Test
    void updateConge() {
        int id = 1;
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        Conge conge = restTemplate.getForObject(urlConge + id, Conge.class);
        conge.setRaison("raisonTest");
        conge.setTypeConge("typeTest");
        conge.setDateDebut(ts);
        conge.setDateFin(ts);
        restTemplate.put(urlConge + id, conge);
        Conge updatedConge = restTemplate.getForObject(urlConge + id, Conge.class);
        assertNotNull(updatedConge);
    }

    @Test
    void deleteConge() {
        int id = 1;
        Conge conge = restTemplate.getForObject(urlConge + id, Conge.class);
        assertNotNull(conge);
        restTemplate.delete(urlConge + id);
    }


}
