package pl.coderslab.planespotter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.planespotter.dto.response.OpenSkyPlaneResponse;
import pl.coderslab.planespotter.service.OpenSkyService;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/opensky")
public class OpenSkyController {

    private final OpenSkyService service;

    public OpenSkyController(OpenSkyService service) {
        this.service = service;
    }


    @GetMapping("/nearby")
    public ResponseEntity<List<OpenSkyPlaneResponse>> near(@RequestParam double la,
                                                           @RequestParam double lo){

        return ResponseEntity.ok(service.getPlanes(la, lo));
    }
}
