package com.example.patientservice.client;

import com.example.patientservice.dto.UsersDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@FeignClient(name="user-ms" ,url = "localhost:8080/user-ms")
public interface UserClient {
    @GetMapping("/api/users")
    public List<UsersDto> findAllUsers();

    @GetMapping(path = "/api/users/oneuser/{id}")
    public UsersDto getOneUser(@PathVariable("id") long id) ;
}
