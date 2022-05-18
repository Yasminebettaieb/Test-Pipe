package com.onegateafrica.entity;
import com.onegateafrica.dto.UsersDto;
import lombok.*;


import javax.persistence.Embeddable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class FormationUsers {

    private long  id  ;
    private List<UsersDto> usersDtoList ;

}
