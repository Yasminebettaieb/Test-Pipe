package com.onegateafrica.controller;
import com.onegateafrica.Service.EmailService;
import com.onegateafrica.Service.UserService;
import com.onegateafrica.client.RhClient;
import com.onegateafrica.dto.EmploiDto;
import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.*;
import com.onegateafrica.repository.TokenRepo;
import com.onegateafrica.repository.UserFormationRespository;
import com.onegateafrica.repository.UserGradeRepository;
import com.onegateafrica.repository.UsersRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
@Api("Users/v1"+"infos")
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/users")
public class UsersController {
  private final UsersRepository usersRepository;
  private final ModelMapper mapper;
  private final UserGradeRepository userGradeRepository;
  private final UserFormationRespository userFormationRespository;
  private final RhClient rhClient;
  private final UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private EmailService emailService;
  private final TokenRepo tokenRepo;
  @Autowired
  private JavaMailSender javaMailSender;
  @Autowired
  public UsersController(EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, RhClient rhClient, UsersRepository usersRepository, ModelMapper mapper, UserGradeRepository userGradeRepository, UserFormationRespository userFormationRespository, TokenRepo tokenRepo) {
    this.rhClient=rhClient;
    this.usersRepository=usersRepository;
    this.mapper=mapper;
    this.userGradeRepository=userGradeRepository;
    this.userFormationRespository=userFormationRespository;
    this.userService=userService;
    this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    this.emailService = emailService;
    this.tokenRepo = tokenRepo;
  }





  @DeleteMapping("/{id}")
  @ApiOperation(value = "delete",notes = "cette API permet de supprimer un utilisatur identifié par son Id en supprimant son emlpo du temps")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "utilisateur  et son emploi du temps supprimés  avec succes")
  })
  public Boolean delete(@PathVariable("id") long id){
    Users users =usersRepository.findById(id).get();
    //ConfirmationToken cd=tokenRepo.findConfirmationTokenByUser(id);
    //System.out.println("******************conf"+cd);
    tokenRepo.delete(tokenRepo.findConfirmationTokenByUser(users));
    usersRepository.deleteById(id);
    return true;
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "update",notes = "cette API permet de modifier un utilisatur bien defini par son Id ")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message = "l'utlisateur est modifié  avec succes"),
          @ApiResponse(code=404 ,message = "l'utlisateur non trouvé ")

  })
  public Users update(@PathVariable(value = "id") Long id, @RequestBody Users users){
    if(usersRepository.findById(id).isPresent()){
      Users u = usersRepository.findById(id).get();
      System.out.println("****************"+u);
      if(!(users.getNom().equals("")))
        u.setNom(users.getNom());
      if(!(users.getPrenom().equals("")))
        u.setPrenom(users.getPrenom());
      if( !(users.getAdresse().equals("")))
        u.setAdresse(users.getAdresse());
      if( !(users.getEmail().equals("")))
        u.setEmail(users.getEmail());
      if(users.getRole() != null)
        u.setRole(users.getRole());
      if(!(users.getCin().equals("")))
        u.setCin(users.getCin());
      if(!(users.getGender().equals("")))
        u.setGender(users.getGender());
      if(users.getDateNaissance() != null)
        u.setDateNaissance(users.getDateNaissance());
      if(!(users.getPhoneNumber().equals("")))
        u.setPhoneNumber(users.getPhoneNumber());
        u.setIdEmploi(users.getIdEmploi());
      System.out.println("******************** res"+u);
      return  usersRepository.save(u);
    }
    else return null;
  }


  @GetMapping("/countUsers")
  public long counter(){
    List<Users> u = usersRepository.findAll();
      int count = 0;
      for(Users i : u)
          count++;
    return count;

  }


  @PutMapping("/modifierEmploi/{id}")
  public Boolean modifierEmploi(@PathVariable("id") long id){
    Users users =usersRepository.findById(id).get();
    users.setIdEmploi(0);
    System.out.println("*****************************");
    System.out.println("userFound");
    usersRepository.save(users);
    return true;
  }
  @PostMapping
  //@PreAuthorize("hasRole('ADMINISTRATEUR') ")
  @ApiOperation(value = "create",notes = "Cette API permet de  créer un user et l'ajouter dans la base ",response = UsersDto.class)
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message = "L'utilisateur est créer avec succes")
  })
  public Users create(@RequestBody UsersDto usersDto ) throws MessagingException {
    if(usersDto==null){
      throw new NoSuchElementException();
    }
    usersDto.setEtatCompte(true);
    String password=usersDto.getPassword();
    usersDto.setPassword(bCryptPasswordEncoder.encode(usersDto.getPassword()));
    Users result = usersRepository.save(mapper.map(usersDto, Users.class));
    ConfirmationToken confirmationToken = new ConfirmationToken(result);
    tokenRepo.save(confirmationToken);
    // de ici
    String lien="http://localhost:8085/api/users/confirm-account?token="+confirmationToken.getConfirmationToken();
    MimeMessage msg = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
    helper.setTo(usersDto.getEmail());
    helper.setSubject("Votre accès à votre compte SmartClinic ");
    helper.setText("<h3>Bonjour "+usersDto.getNom()+" "+usersDto.getPrenom()+" , </h3>" +
            "<p>Bienvenu dans SmartClinic , </p></br> " +
            "<p>Nous sommes très heureux que vous ayez accepté de rejoindre notre clinique et nous sommes impatients de vous compter parmi nous.</p>"+
            "<p> Vous pouvez accéder à notre application et activer l'accès à votre compte en cliquant sur  : "+ "<a href="+lien+">ce lien</a></p></br>"+
            "<p><b>Vos code d'accès :</b></p>"+
            "<ul><li>Adresse e-mail : "+usersDto.getEmail()+"<li>Mot de passe : " +password+"</ul>"+
            "<p><b>Conseils de sécurité importants :</b></p>"+
            "<ol><li>Vos information de compte doivent rester confidentielles. <li>Ne les Communiquez jamais à qui ce soit.<li>Changer votre mot de passe régulirèment.<li>Si vous pensez que quelqu'un utilise votre compte veuillez nous informer immédiatement. </ol>"+"<p>A trés bientot,<p></br><p>SmartClinic</p>", true);
    javaMailSender.send(msg);
    return result;
  }
  @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
  public RedirectView confirmUserAccount(@RequestParam("token")String confirmationToken)
  {
    ConfirmationToken token = tokenRepo.findByConfirmationToken(confirmationToken);
    if(token != null)
    {
      Users u=usersRepository.findUsersByEmail(token.getUser().getEmail()).orElseThrow(()->new NoSuchElementException("user doesn't exist"));
      u.setEtatCompte(true);
      usersRepository.save(u);
    }
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl("http://localhost:4200/#/login");
    return redirectView;
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "findById",notes = "Cette API permet rechercher un user par son Id",response = UsersDto.class)
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message = "l'utilisateur est trouvé avec succes"),
          @ApiResponse(code=404,message = "l'utilisateur' n'est pas trouvé ")
  })
  public Users findById(@PathVariable("id") long id){
    Users users =usersRepository.findById(id).get();
    if(users.getIdEmploi()!=0){
   // users.setEmploiDto(rhClient.findEmploiById(users.getIdEmploi()));
    }else{
      EmploiDto emp=new EmploiDto();
      users.setEmploiDto(emp);

    }
    // bch njib les congés lkol ml congés where l'idUser == l'id de cette user
    return users;
  }

  @PutMapping("/changepassword/{id}")
  @ApiOperation(value = "findById",notes = "Cette API permet rechercher un user par son Id",response = UsersDto.class)
  public Object changePassword(@PathVariable("id") long id ,@RequestBody Compte compte){
    Users user =usersRepository.findById(id).get();
    System.out.println(user.getPassword());
    BCryptPasswordEncoder b = new BCryptPasswordEncoder();
    if(b.matches(compte.getPassword(),user.getPassword())
    ){
      user.setPassword((b.encode(compte.getNewpassword())));
      usersRepository.save(user);
      return user; }
    else

      return "Passwords are not correct";
  }

  @GetMapping("/all")
  @ApiOperation(value = "findAll",notes = "Cette API cherche la  liste de tous les utilisateurs à partir de la base de donnée",responseContainer = "List<UsersDto>")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "liste de users est trouvé  avec succes")
  })
  public List<Users> findAll(){
    List<Users> all = usersRepository.findAll();
    if(all.isEmpty()){
      throw new NoSuchElementException();
    }else{
      return all;
    }
  }



  @GetMapping("/allbyEmloi/{id}")
  /*@PreAuthorize("hasRole('ADMINISTRATEUR') ")*/
  @ApiOperation(value = "findAll",notes = "Cette API cherche la  liste de tous les utilisateurs à partir de la base de donnée",responseContainer = "List<UsersDto>")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "liste de users est trouvé  avec succes")
  })
  public List<UsersDto> findAllbyIdEmploi(@PathVariable("id") long id){
    List<UsersDto> list=new ArrayList<>();
    for (var i : usersRepository.findUserByIdEmploi(id)){
      list.add(mapper.map(i,UsersDto.class));
    }
    return  list;
  }
  /**
   * @author Afli RAMZI
   * @param idUser
   * @param idFormation
   * @return UserFomation
   * @apiNot  this function can replace many  to  many  between formation  and   users
   */
  @PutMapping("/setFormation/{iduser}/{idformation}")
  @ApiOperation(value = "setUserFormation",notes = "cette API permet d'ajouter une formation a un utilisateur en ajoutant comme paramétre l Id du formation et l Id de l'utilisateur",responseContainer = "List<UserFormationDto>")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "formation ajoutée à un utilisateur  avec succes")
  })
  public UserFormation setUserFomration(@PathVariable(value = "iduser")long idUser,
                                        @PathVariable(value = "idformation") long idFormation)
  {
    var  userformation= new  UserFormation(idUser,idFormation );
    userFormationRespository.save(userformation);
    return  userformation ;
  }

  /**
   * @author   achref
   * @return  UsersDto
   * @apiNote : ce methode permet de modifier un utilisateur qui est ce Id.
   */
  @PutMapping(path = "/update/{id}")
  @ApiOperation(value = "updateUser",notes = "cette API permet de modifier un utilisateur bien défini par son Id")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "Utlilisatur modifié avec succes")
  })
  public Users updateUser (@PathVariable(value = "id") Long id, @RequestBody UsersDto userDto){
    try{
      Users u = mapper.map(userDto,Users.class);
      u.setId(id);
      return usersRepository.save(u);}
    catch (EntityNotFoundException e){
      System.out.println (e.getMessage());
      return null;
    }
  }

  /**
   * @author   achref
   * @return  List des UsersDto
   * @apiNote  : ce methode  retourne tous les utilisateurs et envoyer vers le Ms_Rh
   */

  @GetMapping(path = "/user/all")
  @PreAuthorize("hasRole('ADMINISTRATEUR')")
  @ApiOperation(value = "getAll",notes = "cette methode permet de chercher la liste de tous les utilisateurs et l'envoyer vers le microservice Ms-Rh",responseContainer = "List<UsersDto>")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "liste de users est trouvé  avec succes")
  })
  public List<UsersDto> getAll(){
    List<UsersDto> list=new ArrayList<>();
    for (var i : usersRepository.findAll()){
      LocalDate date = i.getDateEmploi().toLocalDateTime().toLocalDate();
      if (((LocalDate.now().getYear()-i.getDateEmploi().getYear())%2==0)&&(LocalDate.now().getMonth().getValue()==date.getMonth().getValue())&&i.getAcceptPromotion())
        list.add(mapper.map(i,UsersDto.class));
    }
    List<UsersDto> listUser=new ArrayList<>();
    for (UsersDto u :list ){
      u.setUserGrade(userGradeRepository.getUserGradeByIdUser(u.getId()));
      listUser.add(u);
    }
    return listUser;
  }
  @GetMapping(path = "/oneuser/{id}")
  @ApiOperation(value = "getuser",notes = "cette methode permet de chercher un utilisateur et l'envoyer vers le microservice Ms-Rh",response = UsersDto.class)
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  " users est trouvé  avec succes")
  })
  public UsersDto getOneUser(@PathVariable("id") long id){
    Users users =usersRepository.findById(id).get();
    if(users.equals(null)){
      throw new NoSuchElementException();
    }else{
      return mapper.map(users, UsersDto.class);}

  }

  @GetMapping(path = "/addEmp/{id}/{idEmp}")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "promotion réfusé avec succes ")
  })
  public boolean AjoutEmploi(@PathVariable(value = "id")long iduser,@PathVariable(value = "idEmp")long idEmp) {

    Users u= usersRepository.findById(iduser).get();
    u.setIdEmploi(idEmp);
    usersRepository.save(u);
    return true;
  }


  /**
   * @author   achref/maher
   * @return   Boolean
   * @apiNote  : ce methode  permet de refuser une promotion
   */
  @PutMapping(path = "/refus/{id}")
  @ApiOperation(value = "refusGrade ",notes = "cette methode permet de de refuser une promotion pour un utilisatur bien defini par son id")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "promotion réfusé avec succes ")
  })
  public boolean refusGrade(@PathVariable(value = "id")long idUser) {
    var user=usersRepository.findById(idUser);
    user.get().setAcceptPromotion(false);
    return false;
  }

  /**
   * @author   achref/maher
   * @return   Boolean
   * @apiNote  : ce methode  permet d'ajouter un grade
   */
  @PutMapping(path = "/accept/{iduser}/{idgrade}")
  @ApiOperation(value = "acceptGrade ",notes = "cette methode permet d'ajouter un gradepour un utilisatur bien defini par son id ")
  @ApiResponses(value ={
          @ApiResponse(code=200 ,message =  "grade ajouter avec succes ")
  })
  public boolean acceptGrade(@PathVariable(value = "iduser")long idUser,@PathVariable(value = "idgrade") long idGrade) {
    UserGrade x=new UserGrade(idUser,idGrade);
    userGradeRepository.save(x);
    return true;
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }
  //*********************************** pour tester********************************
  @GetMapping("/email/{email}")
  public UsersDto findByEmail(@PathVariable("email") String email){
    return userService.findUserByEmail(email);
  }

  // **************************************find by role****************************
  @GetMapping("/role/{role}")
  /*@PreAuthorize("hasRole('TECHNICIAN') ")*/
  public List<Users> findByRole(@PathVariable("role") Roles role) {
    List<Users> all = userService.findUserByRole(role);
    if (all.isEmpty()) {
      throw new NoSuchElementException();
    } else {
      return all;
    }
  }
}
