package com.onegateafrica.repository;

import com.onegateafrica.entity.UserFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFormationRespository extends JpaRepository<UserFormation,Long> {


}
