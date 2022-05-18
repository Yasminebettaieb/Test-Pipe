package com.onegateafrica.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onegateafrica.dto.CongeDto;
import com.onegateafrica.dto.EmploiDto;
import com.onegateafrica.dto.FormationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

public class Compte {
   private String newpassword;
    private String password;
}


