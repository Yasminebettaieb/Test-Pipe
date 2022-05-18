package com.onegateafrica.controller;

import com.onegateafrica.client.UserClient;
import com.onegateafrica.dto.CongeDto;
import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.Conge;
import com.onegateafrica.entity.EmploiDeTemps;
import com.onegateafrica.repository.CongeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
@Api("Rh/v1"+"Conge")
@RestController
@RequestMapping("/api/conge")
@CrossOrigin("http://localhost:4200/")
public class CongeController {
    private final UserClient userClient;
    private final CongeRepository congeRepository;
    private final ModelMapper mapper;
    @Autowired
    public CongeController(CongeRepository congeRepository,ModelMapper mapper,UserClient userClient){
        this.userClient= userClient;
        this.congeRepository= congeRepository;
        this.mapper= mapper;
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "findById",notes = "cette API permet de récuprer un conge  identifié par son Id",response = CongeDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge récupérée avec succes")
    })
    public Conge findById(@PathVariable("id") long id){
        var conge= congeRepository.findById(id).get();
        conge.setUsersDto(userClient.getOneUser(conge.getIdUser()));
        return conge;
    }

    @GetMapping("/getByIdUser/{id}")
    @ApiOperation(value = "findByIdUser",notes = "cette API permet de récuprer un conge  identifié par l'Id de l'utilisateur",response = CongeDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge récupérée avec succes")
    })
    public List<Conge> findByIdUser(@PathVariable("id") long id){
        List<Conge> all = congeRepository.findCongeByIdUser(id);
        for (Conge conge:all ){
            conge.setUsersDto(userClient.getOneUser(conge.getIdUser()));
        }
        if (all.isEmpty()){
            return all;
        }else {
            return all;
        }

    }

    @GetMapping()
    @ApiOperation(value = "findAll",notes = "cette API permet de récuprer le liste de tous les conges de la base",responseContainer = "List<CongeDto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "liste de tous les conges récupérée avec succes")
    })

    // @parma:
    //@return : List<Conge>
    // cette methode  permette  de lister  tous les congés de  la base

    public List<Conge> findAll(){
        List<Conge> all = congeRepository.findAll();
        for (Conge conge:all ){
            conge.setUsersDto(userClient.getOneUser(conge.getIdUser()));               //.setUsersDtos(userClient.getOneUser(conge.getIdUser()));
        }

        if (all.isEmpty()){
            return all;
        }
        else
            return  all;

    }
    @GetMapping("/findAllForAdmin")
    @ApiOperation(value = "findAllForAdmin",notes = "cette API permet de récuprer le liste de tous les conges qui sont accéptes par le chef-Service",responseContainer = "List<CongeDto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "liste de tous les conges récupérée avec succes")
    })

    // @parma:
    //@return : List<Conge>
    // cette methode  permette  de lister  tous les congés de  la base qui sont accépter par le chef service

    public List<Conge> findAllForAdmin(){
        List<Conge> all = congeRepository.findAll();
        List<Conge> allTrueChefService=new ArrayList<> ();
        for (Conge conge:all ) {
            if(conge.getConfirmationChefService()==null||conge.getConfirmationChefService()==false){
            }
            else{
                conge.setUsersDto(userClient.getOneUser(conge.getIdUser()));               //.setUsersDtos(userClient.getOneUser(conge.getIdUser()));
                allTrueChefService.add(conge);
            }
        }
        return allTrueChefService;

    }


    @GetMapping("/acceptCongeAdmin/{id}")
    // @parma:  long  id
    // @return : boolean
    //cette methode  permette  d'accepter  un congé selon  l'id donnee en parametre
    @ApiOperation(value = "acceptConge",notes = "cette API permet d'accepter' la demande un conge  identifié par son Id par l'admin")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge accepté par l'admin ")
    })

    public boolean acceptCongeAdmin(@PathVariable("id") long id) {
        if (id==0)
            throw new NoSuchElementException("no such id exists");
        var conge=congeRepository.getById(id);
        conge.setConfirmationRh(true);
        congeRepository.save(conge);
        return true;
    }

    @GetMapping("/refuseCongeAdmin/{id}")
    // @parma:  long  id
    // @return : boolean
    //cette methode  permette  de refuser  un congé  selon  l'id donnee en parametre
    @ApiOperation(value = "refuseConge",notes = "cette API permet de réfuser la demande un conge  identifié par son Id par l'admin")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge réfusé par l'admin ")
    })

    public boolean refuseCongeAdmin(@PathVariable("id") long id) {
        if (id==0)
            throw new NoSuchElementException("no such id exists");

        var conge=congeRepository.getById(id);
        conge.setConfirmationRh(false);
        congeRepository.save(conge);
        return  true;
    }
    @GetMapping("/acceptCongeChefService/{id}")
    // @parma:  long  id
    // @return : boolean
    //cette methode  permette  d'accepter  un congé selon  l'id donnee en parametre
    @ApiOperation(value = "acceptConge",notes = "cette API permet d'accepter' la demande un conge  identifié par son Id par Chef Service")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge accepté par Chef Service ")
    })

    public boolean acceptCongeChefService(@PathVariable("id") long id) {
        if (id==0)
            throw new NoSuchElementException("no such id exists");
        var conge=congeRepository.getById(id);
        conge.setConfirmationChefService(true);
        congeRepository.save(conge);
        return true;
    }

    @GetMapping("/refuseCongeChefService/{id}")
    // @parma:  long  id
    // @return : boolean
    //cette methode  permette  de refuser  un congé  selon  l'id donnee en parametre
    @ApiOperation(value = "refuseConge",notes = "cette API permet de réfuser la demande un conge  identifié par son Id par Chef Service")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge réfusé  par chef service ")
    })

    public boolean refuseCongeChefService(@PathVariable("id") long id) {
        if (id==0)
            throw new NoSuchElementException("no such id exists");

        var conge=congeRepository.getById(id);
        conge.setConfirmationChefService(false);
        congeRepository.save(conge);
        return  true;
    }



    @PostMapping
    @ApiOperation(value = "create",notes = "cette API permet de créer un conge et l'ajouter dans la base")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge ajouté avec succes")
    })
    public Conge create(@Valid @RequestBody CongeDto congeDto ){
        if(congeDto==null)
            throw new NoSuchElementException();
        return congeRepository.save(mapper.map(congeDto, Conge.class));
    }
    @PutMapping("/{id}")
    @ApiOperation(value = "updateConge",notes = "cette API permet de modifier un conge  identifié par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge modifié  avec succes")
    })
    public Conge updateConge(@PathVariable("id") long id , @RequestBody CongeDto congeDto ){
        Conge congeDb = congeRepository.findById(id).get();
        if(congeDto.getTypeConge()!=null)
            congeDb.setTypeConge(congeDto.getTypeConge());
        /**if(congeDto.getRaison()!=null)
         congeDb.setRaison(congeDto.getRaison());
         */
        if(congeDto.getDateDebut()!=null)
            congeDb.setDateDebut(congeDto.getDateDebut());
        if(congeDto.getDateFin()!=null)
            congeDb.setDateFin(congeDto.getDateFin());
        return congeRepository.save(congeDb);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteConge",notes = "cette API permet de supprimer un conge  identifié par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "conge supprimé  avec succes")
    })
    public Boolean deleteConge(@PathVariable("id") long id){
        var conge=congeRepository.findById(id);
        if (conge.isPresent())
        {
            congeRepository.deleteById(id);
            return true;
        }
        throw new NoSuchElementException("Conge doesn't exist");
    }




    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.append(error.getField() + ": "+ error.getDefaultMessage()+".\n");
        }
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }

}
