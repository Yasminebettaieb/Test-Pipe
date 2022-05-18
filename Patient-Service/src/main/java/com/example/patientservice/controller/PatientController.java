package com.example.patientservice.controller;
import com.example.patientservice.entity.ImagerieMdicale;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.dto.Patientdto;
import com.example.patientservice.repository.ImagerieRepository;
import com.example.patientservice.repository.PatientRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
@Api("Patient/v1"+"infos")
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientRepository patientRepository ;
    private final ModelMapper mapper;
    private final ImagerieRepository imageroieRepository;
    @Autowired
    public PatientController(PatientRepository patientRepository, ModelMapper mapper, ImagerieRepository imageroieRepository) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
        this.imageroieRepository = imageroieRepository;
    }

/***********SUPPRIMER UN PATIENT AVEC L ID******************/


    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete",notes = "cette API permet de supprimer un Patient identifié par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "Patient supprimé  avec succes")
    })
    public Boolean delete(@PathVariable("id") long id){
        Patient p = patientRepository.getById(id);
        List<ImagerieMdicale> m= imageroieRepository.findImagerieMdicaleByPatient(p);
        for(ImagerieMdicale i : m)
            imageroieRepository.delete(i);
        patientRepository.deleteById(id);

        return true;
    }


    /*****************MODIFIER PATIENT************/

    @PutMapping("{id}")
    @ApiOperation(value = "update",notes = "cette API permet de modifier un Patient bien defini par son Id ")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "le patient est modifié  avec succes"),
            @ApiResponse(code=404 ,message = "le patient non trouvé ")

    })
    public Patient update(@PathVariable(value = "id") Long id, @RequestBody Patientdto patientdto){
        if(patientdto==null){
            throw new NoSuchElementException();
        }
        Patient patient= mapper.map(patientdto,Patient.class);
        if(patientRepository.findById(id).isPresent()){
            Patient p = patientRepository.findById(id).get();
            if(!(patient.getNom().equals("")))
                p.setNom(patient.getNom());
            if (!(patient.getPrenom().equals("")))
                p.setPrenom(patient.getPrenom());
            if(!(patient.getAdresse().equals("")))
                p.setAdresse(patient.getAdresse());
            if (!(patient.getEmail().equals("")))
                p.setEmail(patient.getEmail());
            if(!(patient.getCin().equals("")))
                p.setCin(patient.getCin());
            if(patient.getDateNaissance()!= null)
                p.setDateNaissance(patient.getDateNaissance());
            if(!(patient.getTelephone().equals("")))
                p.setTelephone(patient.getTelephone());
            if(!(patient.getGender().equals("")))
                p.setGender(patient.getGender());

            return  patientRepository.save(p);
        }
        else return null;

    }

    @GetMapping("/countPatients")
    public long counter(){
        List<Patient> u = patientRepository.findAll();
        int count = 0;
        for(Patient i : u)
            count++;
        return count;

    }
/****************POST**************/

    @PostMapping
    @ApiOperation(value = "create",notes = "Cette API permet de  créer un user et l'ajouter dans la base ",response = Patientdto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "L'utilisateur est créer avec succes")
    })
    public Patient create(@RequestBody Patientdto patientdto ){
        if(patientdto==null){
            throw new NoSuchElementException();
        }
        return patientRepository.save(mapper.map(patientdto, Patient.class));
    }


    /*****************chercher********************/

    @GetMapping("/{id}")
    @ApiOperation(value = "findById",notes = "Cette API permet rechercher un user par son Id",response = Patient.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "l'utilisateur est trouvé avec succes"),
            @ApiResponse(code=404,message = "l'utilisateur' n'est pas trouvé ")
    })
    public Patient findById(@PathVariable("id") long id){
        Patient patient =patientRepository.findById(id).get();
        return patient;
    }

    /*********************consulter***********/
    @GetMapping
    @ApiOperation(value = "findAll",notes = "Cette API cherche la  liste de tous les Patients à partir de la base de donnée",responseContainer = "List<Patientdto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "liste de users est trouvé  avec succes")
    })
    public List<Patient> findAll(){
        List<Patient> all = patientRepository.findAll();
        if(all.isEmpty()){
            return all;
        }else{
            return all;
        }
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
