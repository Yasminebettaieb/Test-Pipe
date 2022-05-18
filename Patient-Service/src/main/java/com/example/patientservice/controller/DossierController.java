package com.example.patientservice.controller;

import com.example.patientservice.entity.*;
import com.example.patientservice.repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
@Api("Patient/v1"+"infos")
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/dossier")

public class DossierController {

        private final OrdonnanceRepository ordonnanceRepository;
        private final ConsultationRepository consultationRepository;
        private final MedicamentRepository medicamentRepository;
        private final PatientRepository patientRepository;
        private final RdvRepository rdvRepository;
        private final ImagerieRepository  imagerieRepository;
        private final ModelMapper mapper;

        @Autowired
        public DossierController(OrdonnanceRepository ordonnanceRepository, ConsultationRepository consultationRepository, MedicamentRepository medicamentRepository, PatientRepository patientRepository, RdvRepository rdvRepository, ImagerieRepository imagerieRepository, ModelMapper mapper) {
            this.ordonnanceRepository = ordonnanceRepository;
            this.consultationRepository = consultationRepository;
            this.medicamentRepository = medicamentRepository;
            this.patientRepository = patientRepository;
            this.rdvRepository = rdvRepository;
            this.imagerieRepository = imagerieRepository;
            this.mapper = mapper;
        }


        @GetMapping("/consultationPatient/{id}")
        public List<Consultation> findConsultations(@PathVariable("id") long id) {
            List <Consultation> consultationListt  = consultationRepository.findAll();
            List <Consultation> newconsultationList = new LinkedList<>();

            for ( Consultation  c : consultationListt
                 ) {  if(c.getId_patient()==id)
                     newconsultationList.add(c);
            }
                return newconsultationList;

        }
        /**********List des Ordonnance per patient*****/
        @GetMapping("/ordonnanceperpatient/{idPatient}")
        public List<Ordonnance> findAll(@PathVariable("idPatient") long id ) {
            List <Consultation> consultationListt  = consultationRepository.findAll();
            List <Consultation> newconsultationList = new LinkedList<>();
            for ( Consultation  c : consultationListt
            ) {  if(c.getId_patient()==id)
                newconsultationList.add(c);
            }
            List <Ordonnance> ordonnanceList = new LinkedList<>();
            for (Consultation c: newconsultationList
                 ) { ordonnanceList.add(c.getOrdonnance());
            }
           return  ordonnanceList;
        }
        /*********Get patient info****/

    @GetMapping("/patient/{id}")
    @ApiOperation(value = "findById",notes = "Cette API permet rechercher un user par son Id",response = Patient.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "l'utilisateur est trouvé avec succes"),
            @ApiResponse(code=404,message = "l'utilisateur' n'est pas trouvé ")
    })
    public Patient findById(@PathVariable("id") long id){
        Patient patient =patientRepository.findById(id).get();
        return patient;
    }



    /*********GetImagerieMedicale***********/
    @GetMapping("/imagerie/{idPatient}")
    public List<ImagerieMdicale> findImagerie(@PathVariable("idPatient") long idPatient) {

        //getimagerielist
        List <ImagerieMdicale> imagerieList  = imagerieRepository.findAll();
        List <ImagerieMdicale> newimagerieList = new LinkedList<>();
        //gettheimagesbyid

        for ( ImagerieMdicale  c : imagerieList
        ) {  if(c.getPatient().getId_patient()==idPatient)
            newimagerieList.add(c);
        }

            return newimagerieList;

    }

    /*********GetRdvs***********/
    @GetMapping("/rdv/{idPatient}")
    public List<Rdv> findRdv(@PathVariable("idPatient") long idPatient) {

        //getimagerielist
        List <Rdv> rdvList  = rdvRepository.findAll();
        List <Rdv> newRdvList = new LinkedList<>();
        //gettheimagesbyid

        for ( Rdv  r : rdvList
        ) {  if(r.getPatient().getId_patient()==idPatient)
            newRdvList.add(r);
        }

            return newRdvList;

    }





    }



