package com.example.patientservice.dto;

import com.example.patientservice.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagerieMedicaleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_imagerie;
    private String type;
    private String name;
    private String imageUrl;
    private String imageId;
    @Transient
    private Patient patient;
}
