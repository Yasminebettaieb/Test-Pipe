package com.onegateafrica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Conge;

import java.util.List;
import java.util.Optional;

@Repository
public interface CongeRepository extends JpaRepository<Conge,Long> {
    List<Conge> findCongeByIdUser(Long id);

}
