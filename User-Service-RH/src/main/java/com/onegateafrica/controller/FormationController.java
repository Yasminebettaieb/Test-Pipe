package com.onegateafrica.controller;

import com.onegateafrica.client.UserClient;
import com.onegateafrica.dto.*;
import com.onegateafrica.entity.*;
import com.onegateafrica.repository.FormationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api/formation")
@CrossOrigin("http://localhost:4200")
@Api("Rh/v1"+"Formation")

public class FormationController {
    private final FormationRepository formationrepository;
    private final ModelMapper mapper;
    private final UserClient userClient ;


    @Autowired
    FormationController(FormationRepository formationrepository, ModelMapper mapper ,UserClient userClient  ) {
        this.formationrepository = formationrepository;
        this.mapper = mapper;
        this.userClient=userClient;
    }


    @PostMapping("/create")
    @ApiOperation(value = "create",notes = "cette API permet de creer une formation et l'ajouter dans la base")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "formation ajoutée  avec succes"),
            @ApiResponse(code=404 ,message =  "formation non ajoutée")

    })

    /**
     * @param: formation dto
     * @return : String : indicateur  de  realisation de cette  methode
     * cette methode donc permette de  cree et  stocke les formations
     */
    public String create(@RequestBody FormationDto formationDto) {
        var formation = mapper.map(formationDto, Formation.class);
        var test = (formation == null);
        if (!test) {
            formationrepository.save(formation);
            return "done !";
        }
        return "error commited  !";
    }

    @DeleteMapping("/desactiver/{id}")
    /**
     *
     * @parma: long  id
     * @return : String
     * cette methode  permette  d'effacer  une formation de  la base selon  l'id donnee en parametre
     * puis elle traite  l'exciption
     */
    @ApiOperation(value = "delete",notes = "cette API permet de supprimer une formation  identifiée par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "formation supprimée avec succes"),
            @ApiResponse(code=404 ,message =  "formation non trouvée ")

    })

    public Formation delete(@PathVariable("id") long id) {
        Optional<Formation> formationOptional = formationrepository.findById(id);
        return formationOptional.orElseThrow(() -> new NoSuchElementException("no element "));

    }

    @GetMapping("/all")
    /**
     * @param: nothing
     * @return  :tous  les formation  stockes dans  la  base de  donnee sous forme  d'une liste
     */
    @ApiOperation(value = "findall",notes = "cette API permet de récupérer la liste des formations  ",responseContainer = "List<FormationDto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "formation récuperer avec succes"),
            @ApiResponse(code=404 ,message =  "Erreur : aucune formation trouvée ")

    })
    public List<Formation> findall() {
        return formationrepository.findAll();
    }

    @GetMapping("getone/{id}")
    /**
     * @param : long id
     * @return  : la formation  a  chercher
     * cette methode  nous  permet  de trouver une   formation  selon   l'id  donnee
     */
    @ApiOperation(value = "getone",notes = "cette API permet de récuperer une formation  identifiée par son Id",response= FormationDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "formation recuperée avec succes"),
            @ApiResponse(code=404 ,message =  "formation non trouvée ")

    })
    public Formation getone(@PathVariable("id") long id) {
        Optional<Formation> formation = formationrepository.findById(id);
        return formation.orElseThrow(() -> new NoSuchElementException(" there  is  no element  with  such id"));
    }


    @PutMapping("update/{id}")
    /**
     * @param id  formation  + formation Dto
     * @return Formation  updated
     *  cette  methode  permette de faire  un mise  a jour a une formation
     */
    @ApiOperation(value = "update",notes = "cette API permet de modifier une formation  identifiée par son Id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "formation modifiée avec succes"),
            @ApiResponse(code=404 ,message =  "formation non trouvée ")

    })
    public String update(@PathVariable("id") long id, @RequestBody FormationDto formationDto) {
        var formation = mapper.map(formationDto, Formation.class);
        var test = (formationrepository.findById(id).isPresent() && formation == null);
        if (!test) {
            formation.setId(formationrepository.findById(id).get().getId());
            formationrepository.save(formation);
            return "done  ! ";
        }
        return "error  commited   !";
    }

    /**
     *
     * @param id
     * @return Formation
     */
    @PutMapping("/setUsers/{id}")
    @ApiOperation(value = "setUserFormation",notes = "cette API permet d'ajouter une formation  identifiée par son Id a un utlisateur identifié par son id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "formation ajoutée avec succes"),
            @ApiResponse(code=404 ,message =  "formation non ajoutée ")

    })
    public Formation  setUserFormation(@PathVariable long id )
    {
        Optional<Formation> formation = formationrepository.findById(id);
        formation.get().setUsersDtos(userClient.findAllUsers());
        return   formationrepository.save(formation.get());
    }
}