package com.onegateafrica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation,Long> {

}
