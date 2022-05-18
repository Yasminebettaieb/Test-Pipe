package com.example.patientservice.controller;


import com.example.patientservice.entity.Consultation;
import com.example.patientservice.entity.Medicament;
import com.example.patientservice.entity.Ordonnance;
import com.example.patientservice.repository.ConsultationRepository;
import com.example.patientservice.repository.MedicamentRepository;
import com.example.patientservice.repository.OrdonnanceRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Api("Patient/v1"+"infos")
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/ordonnance")
public class OrdonnanceController {

    private final OrdonnanceRepository ordonnanceRepository;
    private final ConsultationRepository consultationRepository;
    private final MedicamentRepository medicamentRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrdonnanceController(OrdonnanceRepository ordonnanceRepository, ConsultationRepository consultationRepository, MedicamentRepository medicamentRepository, ModelMapper mapper) {
        this.ordonnanceRepository = ordonnanceRepository;
        this.consultationRepository = consultationRepository;
        this.medicamentRepository = medicamentRepository;
        this.mapper = mapper;
    }

    @GetMapping("/medicament")
    public List<Medicament> findAllmeds() {
        List<Medicament> all = medicamentRepository.findAll();
        if (all.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return all;
        }
    }
    @GetMapping
    public List<Ordonnance> findAll() {
        List<Ordonnance> all = ordonnanceRepository.findAll();
        if (all.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return all;
        }
    }
    @GetMapping("/medicament/{id}")
    public Medicament findMedicament(@PathVariable("id") long id) {
      Medicament m = medicamentRepository.findById(id).get();
      return m;
    }


    @GetMapping("/addordonnance/{id}")
    public Ordonnance create(@PathVariable("id") long id ) {

        Ordonnance o = new Ordonnance();
        Consultation c = consultationRepository.getById(id);
        c.setOrdonnance(o);
        o.setConsultation(id);
        consultationRepository.save(c);
        return ordonnanceRepository.save(o);
    }



    @GetMapping("/getordonnance/{id}")
    public Ordonnance getOrdonnace(@PathVariable("id")long id)
    { Consultation c = consultationRepository.getById(id);
        return c.getOrdonnance();

    }

    @PostMapping("/addmedicament/{id}")
    public String create(@PathVariable("id") long id , @RequestBody Medicament medicament ) {

        Ordonnance o =ordonnanceRepository.getById(id);
        o.getMedicamentList().add(medicament);
        ordonnanceRepository.save(o);

   return "Added!";
    }


    @DeleteMapping("/medicament/{id}/{idOrdonnance}")
    @ApiOperation(value = "delete", notes = "cette API permet de supprimer un ordonnance identifié par l' Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ordonnance supprimé  avec succes")
    })
    public Boolean deleteMedicament(@PathVariable("id") long id,@PathVariable("idOrdonnance") long idOrdonnance) {
        Ordonnance o = ordonnanceRepository.getById(idOrdonnance);
        List <Medicament> medicamentList;
        for (Medicament m : o.getMedicamentList()){
            if  (m.getId()==id){
                  medicamentList = o.getMedicamentList();
                  medicamentList.remove(m);
                  o.setMedicamentList(medicamentList);
                  ordonnanceRepository.save(o);
                  medicamentRepository.deleteById(id);}
        }
        return true;
    }



    /***********SUPPRIMER UN Ordonnance AVEC L ID******************/


    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete", notes = "cette API permet de supprimer un ordonnance identifié par l' Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ordonnance supprimé  avec succes")
    })
    public Boolean delete(@PathVariable("id") long id) {
        ordonnanceRepository.deleteById(id);
        return true;
    }


    @PutMapping("/updateMedicament/{id}")
    @ApiOperation(value = "update",notes = "cette API permet de modifier une Ordonnance bien defini par son Id ")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "ordonnance est modifié  avec succes"),
            @ApiResponse(code=404 ,message = "ordonnance non trouvé ")

    })
    public Medicament update(@PathVariable(value = "id") Long id, @RequestBody Medicament medicament){
        if(medicament==null){
            throw new NoSuchElementException();
        }
        if(medicamentRepository.findById(id).isPresent()) {
                Medicament m = medicamentRepository.findById(id).get();
            if(!(medicament.getNom().equals("")))
                m.setNom(medicament.getNom());
            if(!(medicament.getMedicamentType().equals("")))
                m.setMedicamentType(medicament.getMedicamentType());
            if(!(medicament.getInstructions().equals("")))
                m.setInstructions(medicament.getInstructions());


            return  medicamentRepository.save(m);
        }
        else{
            throw new NoSuchElementException();
        }
    }
}

