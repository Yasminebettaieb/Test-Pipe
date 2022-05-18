package com.onegateafrica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.EmploiDeTemps;

@Repository
public interface EmploiRepository extends JpaRepository<EmploiDeTemps,Long> {

}
