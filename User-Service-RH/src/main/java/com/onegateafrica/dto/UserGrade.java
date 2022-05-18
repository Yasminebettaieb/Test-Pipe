package com.onegateafrica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGrade {

    private long id;

    private long idUser;
    private long idGrade;
}
