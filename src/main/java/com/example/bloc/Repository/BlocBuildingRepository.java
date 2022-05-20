package com.example.bloc.Repository;
import com.example.bloc.Entity.BlocBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocBuildingRepository extends JpaRepository<BlocBuilding,Long> {
    BlocBuilding findBlocBuildingByName(char Name);

}
