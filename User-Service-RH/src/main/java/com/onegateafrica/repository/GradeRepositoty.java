package com.onegateafrica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Grade;

@Repository
public interface GradeRepositoty extends JpaRepository<Grade,Long> {

}
