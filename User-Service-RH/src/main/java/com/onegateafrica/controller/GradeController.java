package com.onegateafrica.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.validation.Valid;


import com.onegateafrica.client.UserClient;
import com.onegateafrica.dto.CongeDto;
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

import javax.persistence.EntityNotFoundException;


import com.onegateafrica.dto.GradeDto;
import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.Grade;
import com.onegateafrica.repository.GradeRepositoty;



@RestController
@RequestMapping("/api/grade")
@CrossOrigin("http://localhost:4200/")
@Api("Rh/v1"+"grade")

public class GradeController {


    private UserClient userClient;
    private GradeRepositoty gradeRepositoty;
    private ModelMapper mapper;

    @Autowired
    public GradeController(ModelMapper mapper,GradeRepositoty gradeRepositoty, UserClient userClient) {
        this.mapper = mapper;
        this.gradeRepositoty=gradeRepositoty;
        this.userClient=userClient;
    }

    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method getOne permet de récupérer un grade de la base de données
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "findById",notes = "cette API permet de récuprer un grade  identifié par son Id",response = GradeDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "grade récupéré avec succes"),
            @ApiResponse(code=404 ,message =  "grade non trouvé")

    })
    public GradeDto findById(@PathVariable("id") long id){
        return mapper.map(gradeRepositoty.findById(id).orElseThrow(()->new NoSuchElementException("Grade doesn't exist")),GradeDto.class);
    }

    /**
     * @author Ben Nacef Maher
     * @return Grade
     * @method getAll permet de récupérer la list des grade de la base de données
     */
    @GetMapping()
    @ApiOperation(value = "findAll",notes = "cette API permet de récuprer la liste de tous les grade",responseContainer = "List<GradeDto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "grade récupéré avec succes"),
            @ApiResponse(code=404 ,message =  "aucun grade trouvé")

    })
    public List<Grade> findAll(){
        return gradeRepositoty.findAll();
    }

    /**
     * @return Grade
     * @author achref
     * @apiNote : ce methode  permet de retourner tous les users a partie de l'autre ms
     */
    @GetMapping(path = "/user-ms/all")
    @ApiOperation(value = "getUsersfromUserMs",notes = "cette API permet de récuprer la liste de tous les utilisateur",responseContainer = "List<UserDto>")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "utilisateur récupéré avec succes"),
            @ApiResponse(code=404 ,message =  "aucun utilisateur trouvé")

    })
    public List<UsersDto> getUsersfromUserMs(){
        return userClient.findAllUsers();
    }


    /**
     * @return Grade
     * @author achref
     * @apiNote : ce methode  permet de retourner les users par grade
     */
    @GetMapping(path = "/getOne/{id}")
    @ApiOperation(value = "setUsersInGrade",notes = "cette API  permet de retourner les utilisateur par grade",response=GradeDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "utilisateur récupéré avec succes"),
            @ApiResponse(code=404 ,message =  "aucun utilisateur trouvé")

    })

    public Grade setUsersInGrade(@PathVariable("id")long id) throws EntityNotFoundException {
        var x = gradeRepositoty.findById(id).get();
        x.setUsersDtos(userClient.findAllUsers());
        return x;
    }



    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method create permet de creer un grade dans la base de données
     */
    @PostMapping
    @ApiOperation(value = "create",notes = "cette API  permet de creer un grade et l'ajouter dans la base ",response = GradeDto.class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "grade ajouté avec succes"),
            @ApiResponse(code=404 ,message =  "grade non ajouter")

    })

    public GradeDto create(@Valid @RequestBody GradeDto gradeDto ){
        if(gradeDto == null)
            throw new NoSuchElementException();
        gradeRepositoty.save(mapper.map(gradeDto,Grade.class));
        return gradeDto;
    }

    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method update permet de faire le mise a jour d'un grade dans la base de données
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "updateGrade",notes = "cette API  permet de modifier un grade identifié par son id",response = GradeDto .class)
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "utilisateur récupéré avec succes"),
            @ApiResponse(code=404 ,message =  "aucun utilisateur trouvé")

    })
    public GradeDto updateGrade(@PathVariable("id") long id , @RequestBody GradeDto entity ){
        var gradeDb = gradeRepositoty.findById(id).get();
        if (entity.getNom()!=null)
            gradeDb.setNom(entity.getNom());
        if (entity.getDatePromotion()!=null)
            gradeDb.setDatePromotion(entity.getDatePromotion());
        gradeRepositoty.save(gradeDb);
        return mapper.map(gradeDb,GradeDto.class);
    }
    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method delete permet de supprimer un grade de la base de données
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteGrade",notes = "cette API  permet de supprimer un grade identifié par son id")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "grade supprimé avec succes"),
            @ApiResponse(code=404 ,message =  "aucun grade  trouvé")

    })
    public Boolean deleteGrade(@PathVariable("id") long id){
        var grade=gradeRepositoty.findById(id);
        if (grade.isPresent())
        {
            gradeRepositoty.deleteById(id);
            return true;
        }
        throw new NoSuchElementException("Grade doesn't exist");
    }
    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method attestationTravail1 permet de préparer l'entete de l'attestation de travail
     */
    public void attestationTravail1() throws IOException {
        BufferedImage image = ImageIO.read(new File("attestation.png"));
        Graphics g = image.getGraphics();
        g.setFont(new Font("default", Font.BOLD, 90));
        g.setColor(Color.black);
        g.drawString("Attestation de travail",600,150);
        g.dispose();
        ImageIO.write(image,"png",new File("output.png"));
    }

    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method attestationTravail2 permet de préparer le corp de l'attestation de travail avec les données de l'emploiyé
     */
    @PostMapping("attestationTravail")
    @ApiOperation(value = "attestationTravail2",notes = "cette API  permet de préparer le corps de l'attestation de travail avec les données de l'emploiyé")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "attestation crée  avec succes")
    })
    public String attestationTravail2( @RequestBody UsersDto usersDto ) throws IOException {
        attestationTravail1();
        BufferedImage image = ImageIO.read(new File("output.png"));
        Graphics g = image.getGraphics();
        g.setFont(new Font("default", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("Je soussigné  Mohamed Tounsi Gérant de la société CliniSeven, \n",100,400);
        g.drawString("atteste par la présente que Mr "+usersDto.getNom()+" "+usersDto.getPrenom()+" titulaire de la CIN",100,550);
        g.drawString(usersDto.getCin()+", a été salarié (e) au sein de notre société du "+usersDto.getDateEmploi()+" au "+ LocalDate.now(),100,700);
        g.drawString("A Tunis le "+ LocalDate.now(),100,1500);
        g.drawString("Signature ",1500,1500);
        g.dispose();
        ImageIO.write(image,"png",new File("output2.png"));

        return "Attestation creer avec succes !!";
    }
    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method attestationStage permet de préparer l'entete de l'attestation de stage
     */
    public void attestationStage() throws IOException {
        BufferedImage image = ImageIO.read(new File("attestation.png"));
        Graphics g = image.getGraphics();
        g.setFont(new Font("default", Font.BOLD, 90));
        g.setColor(Color.black);
        g.drawString("Attestation de Stage",600,150);
        g.dispose();
        ImageIO.write(image,"png",new File("Stageoutput.png"));
    }
    /**
     * @author Ben Nacef Maher
     * @return GradeDto
     * @method attestationStage2 permet de préparer le corp de l'attestation de stage avec les données de stagiaire
     */
    @PostMapping("attestationStage")
    @ApiOperation(value = "attestationStage2",notes = "cette API  permet de préparer le corps de l'attestation de travail avec les données de stagiaire")
    @ApiResponses(value ={
            @ApiResponse(code=200 ,message =  "attestation crée  avec succes")
    })

    public String attestationStage2( @RequestBody UsersDto usersDto ) throws IOException {
        attestationStage();
        BufferedImage image = ImageIO.read(new File("Stageoutput.png"));
        Graphics g = image.getGraphics();
        g.setFont(new Font("default", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("Je soussigné  Mohamed Tounsi Gérant de la société CliniSeven, \n",100,400);
        g.drawString("atteste par la présente que Mr "+usersDto.getNom()+" "+usersDto.getPrenom()+" titulaire de la CIN",100,550);
        g.drawString(usersDto.getCin()+", a été stagaire au sein de notre société du "+usersDto.getDateEmploi()+" au "+ LocalDate.now(),100,700);
        g.drawString("A Tunis le "+ LocalDate.now(),100,1500);
        g.drawString("Signature ",1500,1500);
        g.dispose();
        ImageIO.write(image,"png",new File("Stageoutput2.png"));

        return "Attestation creer avec succes !!";
    }




    /**
     * @author Ben Nacef Maher
     * @return ResponseEntity
     * @method cette methode permet de traité les exceptions de type NoSuchElementException
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * @author Ben Nacef Maher
     * @return ResponseEntity
     * @method cette methode permet de traité les exceptions de type MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.append(error.getField() + ": "+ error.getDefaultMessage()+".\n");
        }
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }
}