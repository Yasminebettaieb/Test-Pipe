package com.example.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ImagerieMdicale implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_imagerie;
    private String nameImage;
    private String imageUrl;
    private String imageId;
    private String nameRapport;
    private String rapportUrl;
    private String rapportId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp dateCreation;
    public ImagerieMdicale(String original_filename, String url, String public_id, String original_filename2, String url2, String public_id2, Patient patient, String type, Timestamp date) {
        this.nameImage=original_filename;
        this.imageUrl=url;
        this.imageId=public_id;
        this.nameRapport=original_filename2;
        this.rapportUrl=url2;
        this.rapportId=public_id2;
        this.patient=patient;
        this.type=type;
        this.dateCreation=date;
    }
}
