package com.onegateafrica.Security.services;


import com.onegateafrica.Service.UserService;
import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.Users;
import com.onegateafrica.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserService userService;
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UsersDto users = userService.findUserByEmail(email);
    if (users==null)new UsernameNotFoundException("User Not Found with email : " + users.getEmail());
    return UserDetailsImpl.build(users);
  }

}
