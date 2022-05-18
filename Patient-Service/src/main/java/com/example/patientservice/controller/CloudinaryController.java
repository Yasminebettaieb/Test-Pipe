package com.example.patientservice.controller;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.patientservice.dto.ImagerieMedicaleDto;
import com.example.patientservice.dto.Patientdto;
import com.example.patientservice.entity.ImagerieMdicale;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.repository.ImagerieRepository;
import com.example.patientservice.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/cloud")
@RestController
public class CloudinaryController {
    private final ImagerieRepository imagerieRepository;
    private final ModelMapper mapper;
    private final PatientRepository patientRepository;

    @Autowired
    public CloudinaryController(ImagerieRepository imagerieRepository, ModelMapper mapper, PatientRepository patientRepository) {
        this.imagerieRepository = imagerieRepository;
        this.mapper = mapper;
        this.patientRepository = patientRepository;
    }


    //******************************* find all ***************************
    @GetMapping("/getAll")
    public List<ImagerieMdicale> findAll() {
        List<ImagerieMdicale> all = imagerieRepository.findAll();
        if (all.isEmpty()) {
            return all;
        } else {
            return all;
        }
    }


    //******************************* find by Id ***************************
    @GetMapping("/getById/{id}")
    public ImagerieMdicale findById(@PathVariable("id") long id) {
        ImagerieMdicale image = imagerieRepository.findById(id).get();
        return image;
    }

    //******************************* find by public id du rapport  ***************************
    @GetMapping("/getByPublicRapportId/{id}")
    public ImagerieMdicale findByPublicRapportId(@PathVariable("id") String id) {
        ImagerieMdicale image = imagerieRepository.findImagerieMdicaleByRapportId(id);
        return image;
    }



    //******************************* find by public id de l'image ***************************
    @GetMapping("/getByPublicImageId/{id}")
    public ImagerieMdicale findByPublicImageId(@PathVariable("id") String id) {
        ImagerieMdicale image = imagerieRepository.findImagerieMdicaleByImageId(id);
        return image;
    }


    //******************************* delete ***************************
    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") long id) throws IOException {
        ImagerieMdicale image = imagerieRepository.getById(id);
        Map result = deleteFromCloud(image.getImageId());
        System.out.println(result);
        Map resultRapport = deleteFromCloudRapport(image.getRapportId());
        System.out.println(resultRapport);
        imagerieRepository.deleteById(id);
        return true;
    }

    //******************************* delete by public id  ***************************
    @DeleteMapping("/deleteByPublicId/{id}")
    public Boolean deleteByPublicId(@PathVariable("id") String id) throws IOException {
        ImagerieMdicale image = imagerieRepository.findImagerieMdicaleByImageId(id);
        Map result = deleteFromCloud(image.getImageId());
        System.out.println(result);
        imagerieRepository.deleteById(image.getId_imagerie());
        return true;
    }

    //******************************** save **********************************
    public ImagerieMdicale create(@RequestBody ImagerieMedicaleDto imagerieMedicaleDto) {
        if (imagerieMedicaleDto == null) {
            throw new NoSuchElementException();
        }
        return imagerieRepository.save(mapper.map(imagerieMedicaleDto, ImagerieMdicale.class));
    }


    //************************************ post in cloudenary ******************
    @PostMapping("/add")
    public Map addtoCloud(@RequestParam("file") MultipartFile file,
                          @RequestParam("rapport") MultipartFile rapport,
                          @RequestParam("patientId") Long id_Patient,
                          @RequestParam("type")String type ,
                          @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws IOException, ParseException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "oga",
                "api_key", "618949968954215",
                "api_secret", "MbFlvnLAMFxA_Ml9KKezaMN3z04",
                "secure", true));
        Patient patient = patientRepository.getById(id_Patient);
        Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "raw", "public_id", file.getOriginalFilename()));
        Map resultRapport = cloudinary.uploader().upload(rapport.getBytes(), ObjectUtils.asMap("resource_type", "image", "public_id", rapport.getOriginalFilename()));
        Timestamp timestamp = new Timestamp(date.getTime()+ 86400000);
        ImagerieMdicale imagerieMdicale = new ImagerieMdicale(
                (String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"),
                (String) resultRapport.get("original_filename"),
                (String) resultRapport.get("url"),
                (String) resultRapport.get("public_id"),
                (Patient) patient,
                type,
                timestamp);
        imagerieRepository.save(imagerieMdicale);
        return result;
    }

    //************************************ delete from cloudenary ******************
    @DeleteMapping("/deleteCloud/{id}")
    public Map deleteFromCloud(@PathVariable(value = "id") String id) throws IOException {
        Map config = ObjectUtils.asMap(
                "cloud_name", "oga",
                "api_key", "618949968954215",
                "api_secret", "MbFlvnLAMFxA_Ml9KKezaMN3z04",
                "secure", true);
        Cloudinary cloudinary = new Cloudinary(config);
        Map result = cloudinary.uploader().destroy(id,
                ObjectUtils.asMap("resource_type", "raw"));
        return result;
    }

    @DeleteMapping("/deleteCloudRapport/{id}")
    public Map deleteFromCloudRapport (@PathVariable(value = "id") String id) throws IOException {
        Map config = ObjectUtils.asMap(
                "cloud_name", "oga",
                "api_key", "618949968954215",
                "api_secret", "MbFlvnLAMFxA_Ml9KKezaMN3z04",
                "secure", true);
        Cloudinary cloudinary = new Cloudinary(config);
        Map result = cloudinary.uploader().destroy(id,
                ObjectUtils.asMap("resource_type", "image"));
        return result;
    }
}