package com.onegateafrica.repository;

import com.onegateafrica.dto.UsersDto;
import com.onegateafrica.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findUsersByEmail(String email);
    List<Users> findUsersByRole(Roles role);
    List<Users> findUserByIdEmploi(long id);
}
