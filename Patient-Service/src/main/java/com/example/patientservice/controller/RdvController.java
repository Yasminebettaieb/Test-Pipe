package com.example.patientservice.controller;
import com.example.patientservice.client.UserClient;
import com.example.patientservice.dto.RdvDto;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.entity.Rdv;
import com.example.patientservice.repository.RdvRepository;
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
@RequestMapping("/api/rdv")
public class RdvController {
    private final RdvRepository rdvRepository;
    private final ModelMapper mapper;
    private final UserClient userClient;
    @Autowired
    public RdvController(RdvRepository rdvRepository, ModelMapper mapper, UserClient userClient) {
        this.rdvRepository = rdvRepository;
        this.mapper = mapper;
        this.userClient = userClient;
    }
    //**************************** GET BY DATE ********************************
    @GetMapping("/getByDate/{date}")
    @ApiOperation(value = "findById",notes = "Cette API permet rechercher un RDV par son Id",response = Rdv.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est trouvé avec succes"),
            @ApiResponse(code=404,message = " Rdv n'est pas trouvé ")
    })
    public List<Rdv> findByDate(@PathVariable("date") Date date){
        List<Rdv> all = rdvRepository.findRdvByAndDate(date);
        if(all.isEmpty()){
            return all;
        }else{
            //bch njib les info du médecin bil feignclient
            for (Rdv rdv :all ){
                rdv.setMedecin(userClient.getOneUser(rdv.getIdMedecin()));
                //    rdv.setUsersDtos(userClient.getOneUser(emp.getIdUser()));
            }

            return all;
        }
    }
    //**************************** GET BY ID-Medecin ********************************
    @GetMapping("/getByIdMedecin/{id}")
    @ApiOperation(value = "findById",notes = "Cette API permet rechercher un RDV par l'id de médecin",response = Rdv.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est trouvé avec succes"),
            @ApiResponse(code=404,message = " Rdv n'est pas trouvé ")
    })
    public List<Rdv> findByIdMedecin(@PathVariable("id") Long id){
        List<Rdv> all = rdvRepository.findRdvByIdMedecin(id);
        if(all.isEmpty()){
            return all;
        }else{
            //bch njib les info du médecin bil feignclient
            for (Rdv rdv :all ){
                rdv.setMedecin(userClient.getOneUser(rdv.getIdMedecin()));
                //    rdv.setUsersDtos(userClient.getOneUser(emp.getIdUser()));
            }
            return all;
        }
    }
    //***************************** GET All ************************************
    @GetMapping
    @ApiOperation(value = "findAll",notes = "Cette API cherche la  liste de tous les rdvs à partir de la base de donnée",responseContainer = "List<RdvDto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "liste de rdv est trouvé  avec succes")
    })
    public List<Rdv> findAll(){
        List<Rdv> all = rdvRepository.findAll();
        if(all.isEmpty()){
            return all;
        }else{
            return all;
        }
    }
    //***************************** GET BY ID  ************************************
    @GetMapping("/{id}")
    @ApiOperation(value = "findById",notes = "Cette API permet rechercher un RDV par son Id",response = Rdv.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est trouvé avec succes"),
            @ApiResponse(code=404,message = " Rdv n'est pas trouvé ")
    })
    public Rdv findById(@PathVariable("id") long id){
        Rdv rdv = rdvRepository.findById(id).get();
        //rdv.setMedecin(userClient.getOneUser(rdv.getIdMedecin()));
        return rdv;
    }
    //***************************** POST ************************************
    @PostMapping
    @ApiOperation(value = "create",notes = "Cette API permet de  créer un rdv et l'ajouter dans la base ",response = RdvDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est créer avec succes")
    })
    public Rdv create(@RequestBody RdvDto rdvdto ){
        if(rdvdto==null){
            throw new NoSuchElementException();
        }
        return rdvRepository.save(mapper.map(rdvdto, Rdv.class));
    }
    //***************************** PUT ************************************
    @PutMapping("{id}")
    @ApiOperation(value = "update",notes = "cette API permet de modifier un Rdv bien defini par son Id ")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message = "Rdv est modifié  avec succes"),
            @ApiResponse(code=404 ,message = "Rdv non trouvé ")

    })
    public Rdv update(@PathVariable(value = "id") Long id, @RequestBody RdvDto rdvDto){
        if(rdvDto==null){
            throw new NoSuchElementException();
        }
        if(rdvRepository.findById(id).isPresent()) {
            Rdv u = rdvRepository.findById(id).get();
            System.out.println(rdvDto);
            System.out.println(rdvDto.getPatient());
            if((rdvDto.getDate()!= null))
                u.setDate(rdvDto.getDate());
            if((rdvDto.getDateCreation()!= null))
                u.setDateCreation(rdvDto.getDateCreation());
            if((rdvDto.getHeure()!= null))
                u.setHeure(rdvDto.getHeure());
            if(!(rdvDto.getIdMedecin()==0))
                u.setIdMedecin(rdvDto.getIdMedecin());
            if(!(rdvDto.getPatient().getId_patient()==0))
                u.setPatient((Patient) rdvDto.getPatient());
            return  rdvRepository.save(u);
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
        rdvRepository.deleteById(id);
        return true;
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}