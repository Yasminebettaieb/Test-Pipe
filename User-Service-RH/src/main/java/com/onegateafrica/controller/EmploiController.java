package com.onegateafrica.controller;


import com.onegateafrica.client.UserClient;
import com.onegateafrica.dto.EmploiDto;
import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.EmploiDeTemps;
import com.onegateafrica.repository.EmploiRepository;
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
@RestController
@CrossOrigin
@RequestMapping("/api/emploi")
@Api("Rh/v1"+"emploi")


public class EmploiController {

    private final EmploiRepository emploiRepository;
    private final ModelMapper mapper;
    private final UserClient userClient;
    @Autowired
    EmploiController( EmploiRepository emploiRepository, ModelMapper mapper,UserClient userClient){
        this.emploiRepository=emploiRepository;
        this.mapper=mapper;
        this.userClient=userClient;

    }

    /**
     * @author DRIDI Ichrak
     * @return EmploiDeTemps
     * @method findById permet de recuperer un emploi de temps par son id
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "EmploiDeTemps",notes = "cette API permet de récuprer un emploi de temps  identifié par son Id",response = EmploiDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "emploi de temps récupérée avec succes")
    })
    public EmploiDeTemps getOne(@PathVariable("id") long id){
        EmploiDeTemps emploiDeTemps = emploiRepository.findById(id).get();
        //emploiDeTemps.setUsersDtos(userClient.getOneUser(emploiDeTemps.getIdUser()));
        return emploiDeTemps;
    }
    @GetMapping("findOne/{id}")
    public EmploiDto findEmploiById(@PathVariable Long id){
        EmploiDeTemps emploiDeTemps = emploiRepository.findById(id).get();
        if(emploiDeTemps.equals(null)){
            throw new NoSuchElementException();
        }else{
       return mapper.map(emploiDeTemps,EmploiDto.class);}
    }

    /**
     * @author DRIDI Ichrak
     * @return List<EmploiDeTemps>
     * @method findAll permet de recuperer une List d'emploi de temps
     */
    @GetMapping()
    @ApiOperation(value = "findAll",notes = "cette API permet de récuprer les emplois de temps ",responseContainer = "List<EmploiDto")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "la liste de tous les emplois de temps récupérée avec succes"),
            @ApiResponse(code=404 ,message =  "la liste des  emplois de temps est vide")

    })
    public List<EmploiDeTemps> findAll(){
        List<EmploiDeTemps> all = emploiRepository.findAll();
        //for (EmploiDeTemps emp :all ){
        //    emp.setUsersDtos(userClient.getOneUser(emp.getIdUser()));
       // }
        if (all.isEmpty()){
            return all ;
        }else
        {
            return all;
        }
    }

    /**
     * @author DRIDI Ichrak
     * @return Emploi de temps
     * @method create permet de creer Emploi de temps
     */
    @PostMapping
    @ApiOperation(value = "create",notes = "cette API permet de creer un emploi de temps et l'ajouter dans la base")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "emploi de temps crée avec succes")
    })
    public EmploiDeTemps create(@RequestBody EmploiDto emploiDto){

        if(emploiDto==null){
            throw new NoSuchElementException();
        }

        return emploiRepository.save(mapper.map(emploiDto, EmploiDeTemps.class));
    }
    /**
     * @author DRIDI Ichrak
     * @return Emploi De Temps
     * @method update permet de modifier Emploi de temps
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "update",notes = "cette API permet de modifier un emploi de temps  identifié par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "emploi de temps modifie avec succes"),
            @ApiResponse(code=404 ,message =  "emploi de temps non trouvé ")

    })
    public EmploiDeTemps update(@PathVariable("id") long id , @RequestBody EmploiDeTemps edt  ){

        if(emploiRepository.findById(id).isPresent()){
            EmploiDeTemps e = emploiRepository.findById(id).get();
            if((edt.getNombreHeures())!=0)
                e.setNombreHeures(edt.getNombreHeures());
            if((edt.getIdUser())!=0)
                e.setIdUser(edt.getIdUser());
            if((edt.getHeureSupplementaire())!=0)
                e.setHeureSupplementaire(edt.getHeureSupplementaire());

            if(edt.getDateDebut()!=null)
                e.setDateDebut(edt.getDateDebut());
            if(edt.getDateFin()!=null)
                e.setDateFin(edt.getDateFin());
            if(edt.getHeureDebut()!=null)
                e.setHeureDebut(edt.getHeureDebut());
            if(edt.getHeureFin()!=null)
                e.setHeureFin(edt.getHeureFin());
            return  emploiRepository.save(e);
        }
        else return null;
    }
    /**
     * @author DRIDI Ichrak
     * @return boolean
     * @method delete permet de supprimer Emploi de temps
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete",notes = "cette API permet de supprimer un emploi de temps  identifié par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "emploi de temps supprimé avec succes"),
            @ApiResponse(code=404 ,message =  "emploi de temps non trouvé ")

    })
    public Boolean delete(@PathVariable("id") long id){
        EmploiDeTemps emploiDeTemps = emploiRepository.findById(id).get();
        List<UsersDto> list = userClient.findAllbyIdEmploi(id);
        System.out.println("this list ************"+list);
        for(UsersDto u : list) {
            userClient.modifierEmploi(u.getId());
            System.err.println(u.getId());
            System.out.println("updtated ? " + u);
        }
        emploiRepository.deleteById(id);
        return true;
    }
    /**
     * @author DRIDI Ichrak
     * @return ResponseEntity<String>
     * @method handleNoSuchElementException permet de lever une exception de type NoSuchElementException
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
