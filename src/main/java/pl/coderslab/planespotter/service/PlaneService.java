package pl.coderslab.planespotter.service;

import org.springframework.stereotype.Service;
import pl.coderslab.planespotter.dto.request.PlaneRequest;
import pl.coderslab.planespotter.dto.response.PlaneResponse;
import pl.coderslab.planespotter.entity.Airline;
import pl.coderslab.planespotter.entity.Plane;
import pl.coderslab.planespotter.exception.DuplicateResourceException;
import pl.coderslab.planespotter.exception.ResourceNotFoundException;
import pl.coderslab.planespotter.repository.AirlineRepository;
import pl.coderslab.planespotter.repository.PlaneRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaneService {

    private final PlaneRepository planeRepository;
    private final AirlineRepository airlineRepository;


    public PlaneService(PlaneRepository planeRepository, AirlineRepository airlineRepository) {
        this.planeRepository = planeRepository;
        this.airlineRepository = airlineRepository;
    }

    public PlaneResponse toResponse(Plane plane){

        PlaneResponse planeResponse = new PlaneResponse();
        planeResponse.setIcao24(plane.getIcao24());
        planeResponse.setRegistration(plane.getRegistration());
        planeResponse.setId(plane.getId());
        planeResponse.setAirline(plane.getAirline().getName());

        return planeResponse;
    }

    public PlaneResponse create(PlaneRequest planeRequest){

        Map<String, List<String>> errors = new HashMap<>();

        if(planeRepository.existsByIcao24(planeRequest.getIcao24())){
            errors.put("icao24", List.of("A plane with this icao24 already exists"));
        }

        if (!errors.isEmpty()){
            throw new DuplicateResourceException(errors);
        }

        Plane plane = new Plane();

        plane.setIcao24(planeRequest.getIcao24());
        plane.setRegistration(planeRequest.getRegistration());

        if(planeRequest.getAirlineId() != null){
            Airline airline = airlineRepository.findById(planeRequest.getAirlineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

            plane.setAirline(airline);
        }

        return toResponse(planeRepository.save(plane));
    }

    public List<PlaneResponse> findAll() {

        return planeRepository.findAll()
                .stream()
                .map( plane -> toResponse(plane))
                .toList();

    }

    public PlaneResponse findById(Long id){

        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plane not found"));

        return toResponse(plane);
    }

    public void delete(Long id){
        Plane plane = planeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Plane not found"));

        planeRepository.delete(plane);

    }

    public PlaneResponse update(Long id, PlaneRequest planeRequest){

        Map<String, List<String>> errors = new HashMap<>();

        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plane not found"));

        if(planeRepository.existsByIcao24AndIdNot(planeRequest.getIcao24(), id)){
            errors.put("icao24", List.of("A plane with this icao24 already exists"));
        }

        if (!errors.isEmpty()){
            throw new DuplicateResourceException(errors);
        }

        plane.setIcao24(planeRequest.getIcao24());
        plane.setRegistration(planeRequest.getRegistration());

        if (planeRequest.getAirlineId() != null){

            Airline airline = airlineRepository.findById(planeRequest.getAirlineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

            plane.setAirline(airline);
        }else {
            plane.setAirline(null);
        }

        return toResponse(planeRepository.save(plane));

    }
}
