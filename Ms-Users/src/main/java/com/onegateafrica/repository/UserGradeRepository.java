package com.onegateafrica.repository;

import com.onegateafrica.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGradeRepository extends JpaRepository<UserGrade,Long> {

    List<UserGrade> getUserGradeByIdUser(long id);
}
