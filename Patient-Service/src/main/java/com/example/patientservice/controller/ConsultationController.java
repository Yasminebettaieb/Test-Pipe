package com.example.patientservice.controller;
import com.example.patientservice.client.UserClient;
import com.example.patientservice.dto.ConsultationDto;
import com.example.patientservice.dto.RdvDto;
import com.example.patientservice.entity.Consultation;
import com.example.patientservice.entity.Medicament;
import com.example.patientservice.entity.Ordonnance;
import com.example.patientservice.entity.Rdv;
import com.example.patientservice.repository.ConsultationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Api("rdv/v1"+"infos")
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/consultation")
public class ConsultationController {

    private final ConsultationRepository consultationRepository;
    private final ModelMapper mapper;
    private final UserClient userClient;
    @Autowired
    public ConsultationController(ConsultationRepository consultationRepository, ModelMapper mapper, UserClient userClient) {
        this.consultationRepository = consultationRepository;
        this.mapper = mapper;
        this.userClient = userClient;
    }



    //***************************** GET All ************************************
    @GetMapping
    @ApiOperation(value = "findAll",notes = "Cette API cherche la  liste de tous les rdvs à partir de la base de donnée",responseContainer = "List<RdvDto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "liste de rdv est trouvé  avec succes")
    })
    public List<Consultation> findAll() {

        List<Consultation> all = consultationRepository.findAll();
        if (all.isEmpty()) {
            return all;
        } else {
            return all;
        }
    }

    //***************************** GET BY ID  ************************************
    @GetMapping("/{id}")
    @ApiOperation(value = "findById",notes = "Cette API permet rechercher un RDV par son Id",response = Consultation.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est trouvé avec succes"),
            @ApiResponse(code=404,message = " Rdv n'est pas trouvé ")
    })
    public Consultation findById(@PathVariable("id") long id){
        Consultation consultation = consultationRepository.findById(id).get();
        return consultation;
    }


    //***************************** POST ************************************
    @PostMapping
    @ApiOperation(value = "create",notes = "Cette API permet de  créer un rdv et l'ajouter dans la base ",response = RdvDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est créer avec succes")
    })
    public Consultation create(@RequestBody ConsultationDto consultationDto ){
        if(consultationDto==null){
            throw new NoSuchElementException();
        }

        return consultationRepository.save(mapper.map(consultationDto, Consultation.class));}
    //***************************** PUT ************************************
    @PutMapping("{id}")
    @ApiOperation(value = "update",notes = "cette API permet de modifier un Rdv bien defini par son Id ")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est modifié  avec succes"),
            @ApiResponse(code=404 ,message = "Rdv non trouvé ")

    })
    public Consultation update(@PathVariable(value = "id") Long id, @RequestBody Consultation consultationDto){
        if(consultationDto==null){
            throw new NoSuchElementException();
        }
        if(consultationRepository.findById(id).isPresent()) {
            Consultation c = consultationRepository.findById(id).get();
            if(!(consultationDto.getDate().equals("")))
                c.setDate(consultationDto.getDate());
            if(!(consultationDto.getDateCreation().equals("")))
                c.setDateCreation(consultationDto.getDateCreation());
            if(!(consultationDto.getHeure().equals("")))
                c.setHeure(consultationDto.getHeure());
            if(!(consultationDto.getIdMedecin()!= 0))
                c.setIdMedecin(consultationDto.getIdMedecin());
            if(!(consultationDto.getId_patient()!=0))
                c.setId_patient(consultationDto.getId_patient());

            return  consultationRepository.save(c);
        }
        else{
            throw new NoSuchElementException();
        }
    }
    //***************************** DELETE ************************************
    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete",notes = "cette API permet de supprimer un RDV identifié par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "rdv supprimé  avec succes")
    })
    public Boolean delete(@PathVariable("id") long id){
        consultationRepository.deleteById(id);
        return true;
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}




