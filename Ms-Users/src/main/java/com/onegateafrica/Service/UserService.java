package com.onegateafrica.Service;

import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.Roles;
import com.onegateafrica.entity.Users;
import com.onegateafrica.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private UsersRepository usersRepository;
    private ModelMapper mapper;
    @Autowired
    public UserService(UsersRepository usersRepository,ModelMapper mapper) {
        this.usersRepository = usersRepository;
        this.mapper=mapper;
    }
    public UsersDto findUserByEmail(String email){
        Users u=usersRepository.findUsersByEmail(email).orElseThrow(()->new NoSuchElementException("user doesn't exist"));
        if(u.getEtatCompte()){
        return mapper.map(u,UsersDto.class);
        }
        else{
            return null;
        }
    }
    public List<Users> findUserByRole(Roles role){
        List<Users> u=usersRepository.findUsersByRole(role);
        return u;
    }
}
