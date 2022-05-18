package com.onegateafrica.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class UserFormation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private  long id ;
    private  long  idUser;
    private long  idFormation ;
     public  UserFormation( long idUser,  long idFormation)
    {
        this.idUser=idUser;
        this.idFormation=idFormation;
    }
}
