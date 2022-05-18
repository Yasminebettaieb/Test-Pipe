package com.example.bloc.client;

import com.example.bloc.Entity.Bloc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "patient-ms")
public interface PatientClient {

    @GetMapping("/api/findroom/{id}")
    Bloc findBloc(long id);

}

