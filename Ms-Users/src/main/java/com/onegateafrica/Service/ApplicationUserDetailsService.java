package com.onegateafrica.Service;

import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersDto users = userService.findUserByEmail(email);
        List<SimpleGrantedAuthority> authorityList= new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(users.getRole().toString()));
        System.out.println(authorityList);
        return new User(users.getEmail(), users.getPassword(),authorityList);
    }
}
