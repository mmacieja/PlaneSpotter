package pl.coderslab.planespotter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.planespotter.dto.response.OpenSkyPlaneResponse;
import pl.coderslab.planespotter.service.OpenSkyService;
import pl.coderslab.planespotter.service.PlaneMatchingService;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/opensky")
public class OpenSkyController {

    private final OpenSkyService service;

    private final PlaneMatchingService matchingService;

    public OpenSkyController(OpenSkyService service, PlaneMatchingService matchingService) {
        this.service = service;
        this.matchingService = matchingService;
    }



    @GetMapping("/nearby")
    public ResponseEntity<List<OpenSkyPlaneResponse>> near(@RequestParam double la,
                                                           @RequestParam double lo){

        return ResponseEntity.ok(service.getPlanes(la, lo));
    }

    @GetMapping("/findPlane")
    public ResponseEntity<OpenSkyPlaneResponse> findPlane(@RequestParam double la,
                                                           @RequestParam double lo,
                                                          @RequestParam double bearing){

        return ResponseEntity.ok(matchingService.findPlane(la, lo, bearing));
    }
}
