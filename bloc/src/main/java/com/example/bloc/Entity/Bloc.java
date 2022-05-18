package com.example.bloc.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Bloc;
    private Character type;
    private long idpatient;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateEntree;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateSortie;

    private String status;

}








