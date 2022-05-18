package com.onegateafrica.client;


import com.onegateafrica.dto.UsersDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@FeignClient(name="user-ms" ,url = "localhost:8080/user-ms")
public interface UserClient {

    /**
     * @author   achref
     * @return  List des UsersDto
     * @apiNote  : ce methode  retourne tous les utilisateurs en utilisant l'autre microservice Ms_Users
     */
    @GetMapping("/api/users")
    public List<UsersDto> findAllUsers();

    @GetMapping(path = "/api/users/oneuser/{id}")

    public UsersDto getOneUser(@PathVariable("id") long id) ;
    /**
     * @author   achref
     * @return  List des UsersDto
     * @apiNote  : ce methode  retourne tous les utilisateurs en utilisant l'autre microservice Ms_Users
     */
    @GetMapping("/api/users/user/all")
    public List<UsersDto> findAllUsersForGrade();

    /**
     * @author   achref
     * @return  un utilisateur
     * @apiNote : ce methode  retourne un utilisateur qui est ce Id en utilisant l'autre microservice Ms_Users
     */
    @GetMapping("/api/users/user/getuser/{id}")
    public ResponseEntity getUser(@PathVariable long id);

    /**
     * @author   achref
     * @return  UsersDto
     * @apiNote : ce methode permet de modifier un utilisateur qui est ce Id.
     */
    @PutMapping("/api/users/update/{id}")
    public UsersDto updateUser(@PathVariable long id, @RequestBody UsersDto userDto);

    @PutMapping("/api/users/modifierEmploi/{id}")
    public Boolean modifierEmploi(@PathVariable("id") long id);
    @GetMapping("/api/users/allbyEmloi/{id}")
    public List<UsersDto> findAllbyIdEmploi(@PathVariable("id") long id);


}
