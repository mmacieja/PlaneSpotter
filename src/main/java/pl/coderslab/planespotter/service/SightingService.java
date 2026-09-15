package pl.coderslab.planespotter.service;

import org.springframework.stereotype.Service;
import pl.coderslab.planespotter.dto.request.SightingRequest;
import pl.coderslab.planespotter.dto.response.SightingResponse;
import pl.coderslab.planespotter.entity.*;
import pl.coderslab.planespotter.exception.ResourceNotFoundException;
import pl.coderslab.planespotter.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SightingService {

    private final SightingRepository sightingRepository;
    private final PlaneRepository planeRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;


    public SightingService(SightingRepository sightingRepository, PlaneRepository planeRepository, AirlineRepository airlineRepository, AirportRepository airportRepository) {
        this.sightingRepository = sightingRepository;
        this.planeRepository = planeRepository;
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
    }

    public SightingResponse toResponse(Sighting sighting) {

        SightingResponse sightingResponse = new SightingResponse();
        sightingResponse.setTime(sighting.getTime());
        sightingResponse.setCallsign(sighting.getPlane().getCallsign());
        sightingResponse.setId(sighting.getId());
        sightingResponse.setLatitude(sighting.getLatitude());
        sightingResponse.setLongitude(sighting.getLongitude());
        sightingResponse.setIcao24(sighting.getPlane().getIcao24());
        if (sighting.getPlane().getAirline() != null) {
            sightingResponse.setAirline(sighting.getPlane().getAirline().getName());
        }
        if (sighting.getArrivalAirport() != null) {
            sightingResponse.setArrivalAirport(sighting.getArrivalAirport().getName());
        }
        if (sighting.getDepartureAirport() != null) {
            sightingResponse.setDepartureAirport(sighting.getDepartureAirport().getName());
        }

        return sightingResponse;
    }

    public SightingResponse create(SightingRequest request, User user) {


        Airline airline = null;
        if (request.getAirline() != null && !request.getAirline().isBlank()) {
            airline = airlineRepository.findByName(request.getAirline()).orElse(null);
        }

        Plane plane = planeRepository.findByIcao24(request.getIcao24()).orElse(null);

        if (plane == null) {
            plane = new Plane();
            plane.setIcao24(request.getIcao24());
            plane.setCallsign(request.getCallsign());
            plane.setAirline(airline);

            plane = planeRepository.save(plane);
        }
        ;


        Airport arrival = null;

        if (request.getArrivalAirport() != null && !request.getArrivalAirport().isBlank()) {
            arrival = airportRepository.findByName(request.getArrivalAirport()).orElse(null);
        }
        Airport departure = null;

        if (request.getDepartureAirport() != null && !request.getDepartureAirport().isBlank()) {
            departure = airportRepository.findByName(request.getDepartureAirport()).orElse(null);
        }


        Sighting sighting = new Sighting();

        sighting.setUser(user);
        sighting.setPlane(plane);
        sighting.setLatitude(request.getLatitude());
        sighting.setLongitude(request.getLongitude());
        sighting.setTime(LocalDateTime.now());
        sighting.setArrivalAirport(arrival);
        sighting.setDepartureAirport(departure);

        return toResponse(sightingRepository.save(sighting));

    }

    public List<SightingResponse> getSightings(User user) {

        return sightingRepository.findByUser(user)
                .stream()
                .map(sighting -> toResponse(sighting))
                .toList();

    }

    public void deleteSighting(Long id, User user) {

        Sighting sighting = sightingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sighting not found"));

        if (!sighting.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Sighting not found");
        }
        sightingRepository.delete(sighting);
    }
}
