package pl.coderslab.planespotter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.planespotter.dto.response.IdentifiedPlaneResponse;
import pl.coderslab.planespotter.service.FlightLabService;
import pl.coderslab.planespotter.service.OpenSkyService;
import pl.coderslab.planespotter.service.PlaneMatchingService;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/opensky")
public class OpenSkyController {

    private final OpenSkyService service;

    private final PlaneMatchingService matchingService;
    private final FlightLabService flightLabService;

    public OpenSkyController(OpenSkyService service, PlaneMatchingService matchingService, FlightLabService flightLabService) {
        this.service = service;
        this.matchingService = matchingService;
        this.flightLabService = flightLabService;
    }


    @GetMapping("/nearby")
    public ResponseEntity<List<IdentifiedPlaneResponse>> near(@RequestParam double la,
                                                           @RequestParam double lo) {

        return ResponseEntity.ok(service.getPlanes(la, lo));
    }

    @GetMapping("/findPlane")
    public ResponseEntity<IdentifiedPlaneResponse> findPlane(@RequestParam double la,
                                                          @RequestParam double lo,
                                                          @RequestParam double bearing) {
        System.out.println("endpoint called");

        IdentifiedPlaneResponse plane = matchingService.findPlane(la, lo, bearing);

        if (plane == null) {
            System.out.println("plane is null - returning 404");
            return ResponseEntity.notFound().build();
        }

        plane = flightLabService.addToSighting(plane);
        System.out.println("Added more info");

        return ResponseEntity.ok(plane);
    }
}
