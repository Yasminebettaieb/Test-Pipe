package com.example.bloc.Controller;
import com.example.bloc.Entity.BlocBuilding;
import com.example.bloc.Repository.BlocBuildingRepository;
import com.example.bloc.Repository.BlocRepository;
import com.example.bloc.Entity.Bloc;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/blocs")
public class BlocController {


    private final BlocRepository blocRepository;
    private final BlocBuildingRepository blocBuildingRepository;

    public BlocController(BlocRepository blocRepository, BlocBuildingRepository blocBuildingRepository) {
        this.blocRepository = blocRepository;
        this.blocBuildingRepository = blocBuildingRepository;
    }


    @GetMapping("/all")
    public List<Bloc> findallBlocs() {
        List<Bloc> all = blocRepository.findAll();
        if (all.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return all;
        }

    }
    @GetMapping("/findroom/{id}")
    public Bloc findBloc(@PathVariable("id") long id) {
        Bloc b1 = blocRepository.findBlocByIdpatient(id);
        return b1;
    }




    @GetMapping("/Bloc/{id}")
    public Bloc findbloc(@PathVariable("id") long id) {
        Bloc b = blocRepository.findById(id).get();
        return b;
    }

    @GetMapping("/Bloc")
    public List<BlocBuilding> find() {
        List<BlocBuilding> b = blocBuildingRepository.findAll();
        return b;
    }

    @GetMapping("/Building")
    public String createInitializeBloc() {
        for (int i = 1; i < 4; i++) {
            BlocBuilding buildings = new BlocBuilding();
            buildings.setName(((char) (64 + i)));
            blocBuildingRepository.save(buildings);
        }
        return "Blocs initialized";

    }

    @GetMapping("/Building/{type}/")
    public BlocBuilding getByType(@PathVariable("type") char type) {
        return blocBuildingRepository.findBlocBuildingByName(type);

    }

    @GetMapping
    public String createInitialize() {
        for (int i = 0; i < 5; i++) {
            Bloc b = new Bloc();
            b.setType('A');
            blocRepository.save(b);
            BlocBuilding building = blocBuildingRepository.findBlocBuildingByName(b.getType());
            building.getRoom().add(b);
            blocBuildingRepository.save(building);
        }
        for (int i = 0; i < 5; i++) {
            Bloc b = new Bloc();
            b.setType('B');
            blocRepository.save(b);
            BlocBuilding building = blocBuildingRepository.findBlocBuildingByName(b.getType());
            building.getRoom().add(b);
            blocBuildingRepository.save(building);
        }
        for (int i = 0; i < 5; i++) {
            Bloc b = new Bloc();
            b.setType('C');
            blocRepository.save(b);
            BlocBuilding building = blocBuildingRepository.findBlocBuildingByName(b.getType());
            building.getRoom().add(b);
            blocBuildingRepository.save(building);
        }
        return "Blocs initialized";

    }

    @PostMapping("/addPatientinTheBloc")
    public String addPatient( @RequestBody Bloc bloc) {
        if (bloc.getType() == 'A') {
            BlocBuilding blocBuilding = blocBuildingRepository.findBlocBuildingByName('A');
            for (Bloc b : blocBuilding.getRoom()) {
                if (b.getIdpatient() == 0) {
                    b.setIdpatient(bloc.getIdpatient());
                    b.setDateEntree(bloc.getDateEntree());
                    b.setDateSortie(bloc.getDateSortie());
                    b.setType(bloc.getType());
                    b.setStatus("Non Disponible");
                    blocBuildingRepository.save(blocBuilding);
                    break;
                }
            }
        } else if (bloc.getType() == 'B') {
            BlocBuilding blocBuilding = blocBuildingRepository.findBlocBuildingByName('B');
            for (Bloc b : blocBuilding.getRoom()) {
                if (b.getIdpatient() == 0) {
                    b.setIdpatient(bloc.getIdpatient());
                    b.setDateEntree(bloc.getDateEntree());
                    b.setDateSortie(bloc.getDateSortie());
                    b.setType(bloc.getType());
                    b.setStatus("Non Disponible");
                    blocBuildingRepository.save(blocBuilding);
                    break;
                }
            }
        } else if (bloc.getType() == 'C') {
            BlocBuilding blocBuilding = blocBuildingRepository.findBlocBuildingByName('C');
            for (Bloc b : blocBuilding.getRoom()) {
                if (b.getIdpatient() == 0) {
                    b.setIdpatient(bloc.getIdpatient());
                    b.setDateEntree(bloc.getDateEntree());
                    b.setDateSortie(bloc.getDateSortie());
                    b.setType(bloc.getType());
                    b.setStatus("Non Disponible");
                    blocBuildingRepository.save(blocBuilding);
                    break;
                }
            }
        } else {
            return "ALL BOOKED :( ! ";
        }
        return "Added";
    }


    @PutMapping("/{idBloc}")
    public Boolean deletePatientfromBloc(@PathVariable("idBloc") long idBloc) {
        List<BlocBuilding> blocBuildings = blocBuildingRepository.findAll();
        for (BlocBuilding b : blocBuildings
        ) {
            for (Bloc room : b.getRoom()
            ) {
                if (room.getId_Bloc() == idBloc) {
                    room.setIdpatient(0);
                    room.setStatus(null);
                    room.setDateSortie(null);
                    room.setDateEntree(null);
                    blocBuildingRepository.save(b);
                    break;
                }
            }

        }
        return true;
    }




    @PutMapping("/change/{id}")
    public String changePatient(@PathVariable(value = "id") Long id, @RequestBody Bloc bloc) {
        if (bloc == null) {
            throw new NoSuchElementException();
        }
        BlocBuilding blocBuilding = blocBuildingRepository.findBlocBuildingByName(bloc.getType());
        for (Bloc room : blocBuilding.getRoom()
        ) {
            if (room.getId_Bloc() == id) {
                if (!(bloc.getStatus().equals("")))
                    room.setStatus(bloc.getStatus());
                if(bloc.getDateEntree()!=null)
                    room.setDateEntree(bloc.getDateEntree());
                if(bloc.getDateSortie()!=null)
                    room.setDateSortie(bloc.getDateSortie());

                if (!(bloc.getType().equals("")))
                    room.setType(bloc.getType());
                if (bloc.getIdpatient() != 0)
                    room.setIdpatient(bloc.getIdpatient());
            }
        }
         blocBuildingRepository.save(blocBuilding);
        return "Modifed  :) !";

    }
}









