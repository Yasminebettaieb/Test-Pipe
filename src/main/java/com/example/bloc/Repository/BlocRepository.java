package com.example.bloc.Repository;

import com.example.bloc.Entity.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlocRepository extends JpaRepository<Bloc,Long> {
    Bloc findBlocByIdpatient(long id);
}
