package com.onegateafrica.client;

import com.onegateafrica.dto.CongeDto;
import com.onegateafrica.dto.EmploiDto;
import com.onegateafrica.dto.UsersDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "rh-ms")
public interface RhClient {
    /**
     * @author DRIDI Ichrak
     * @return EmploiDto
     * @method findEmploiById permet de recuperer Emploi de temps par son ID
     */
    @GetMapping("/api/emploi/findOne/{id}")
    public EmploiDto findEmploiById(@PathVariable Long id);
    @DeleteMapping("/api/emploi/{id}")
    public Boolean delete(@PathVariable("id") long id);
//    @GetMapping("/getByIdUser/{id}")
//    public List<CongeDto> findByIdUser(@PathVariable("id") long id);

    }
