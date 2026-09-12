package pl.coderslab.planespotter.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.planespotter.dto.request.SightingRequest;
import pl.coderslab.planespotter.dto.response.SightingResponse;
import pl.coderslab.planespotter.service.SightingService;

import java.util.List;

@RestController
@RequestMapping("/sighting")
public class SightingController {

    private final SightingService sightingService;

    public SightingController(SightingService sightingService) {
        this.sightingService = sightingService;
    }

    @PostMapping
    public ResponseEntity<SightingResponse> add(@Valid @RequestBody SightingRequest request,
                                                Authentication authentication) {

        SightingResponse response = sightingService.create(request, authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

    @GetMapping("/all")
    public ResponseEntity<List<SightingResponse>> findAll(Authentication authentication) {

        return ResponseEntity.ok(sightingService.getSightings(authentication.getName()));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSighting(@PathVariable Long id,
                                               Authentication authentication){
        sightingService.deleteSighting(id, authentication.getName());

        return ResponseEntity.noContent().build();
    }
}


