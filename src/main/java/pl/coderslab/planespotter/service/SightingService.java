package pl.coderslab.planespotter.service;

import org.springframework.stereotype.Service;
import pl.coderslab.planespotter.dto.request.SightingRequest;
import pl.coderslab.planespotter.dto.response.SightingResponse;
import pl.coderslab.planespotter.entity.Plane;
import pl.coderslab.planespotter.entity.Sighting;
import pl.coderslab.planespotter.entity.User;
import pl.coderslab.planespotter.exception.ResourceNotFoundException;
import pl.coderslab.planespotter.repository.PlaneRepository;
import pl.coderslab.planespotter.repository.SightingRepository;
import pl.coderslab.planespotter.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SightingService {

    private final SightingRepository sightingRepository;
    private final UserRepository userRepository;
    private final PlaneRepository planeRepository;


    public SightingService(SightingRepository sightingRepository, UserRepository userRepository, PlaneRepository planeRepository) {
        this.sightingRepository = sightingRepository;
        this.userRepository = userRepository;
        this.planeRepository = planeRepository;
    }

    public SightingResponse toResponse(Sighting sighting) {

        SightingResponse sightingResponse = new SightingResponse();
        sightingResponse.setTime(sighting.getTime());
        sightingResponse.setCallsign(sighting.getPlane().getCallsign());
        sightingResponse.setId(sighting.getId());
        sightingResponse.setLatitude(sighting.getLatitude());
        sightingResponse.setLongitude(sighting.getLongitude());
        sightingResponse.setIcao24(sighting.getPlane().getIcao24());
        sightingResponse.setUsername(sighting.getUser().getUsername());

        return sightingResponse;
    }

    public SightingResponse create(SightingRequest request, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Plane plane = planeRepository.findByIcao24(request.getIcao24())
                .orElseGet(() -> {
                    Plane newPlane = new Plane();
                    newPlane.setIcao24(request.getIcao24());
                    newPlane.setCallsign(request.getCallsign());

                    return planeRepository.save(newPlane);
                });


        Sighting sighting = new Sighting();

        sighting.setUser(user);
        sighting.setPlane(plane);
        sighting.setLatitude(request.getLatitude());
        sighting.setLongitude(request.getLongitude());
        sighting.setTime(LocalDateTime.now());

        return toResponse(sightingRepository.save(sighting));

    }

    public List<SightingResponse> getSightings(String username) {

        return sightingRepository.findByUserUsername(username)
                .stream()
                .map(sighting -> toResponse(sighting))
                .toList();

    }

    public void deleteSighting(Long id, String username){

        Sighting sighting = sightingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sighting not found"));

        if(!sighting.getUser().getUsername().equals(username)){
            throw new ResourceNotFoundException("Sighting not found");
        }
        sightingRepository.delete(sighting);
    }
}
