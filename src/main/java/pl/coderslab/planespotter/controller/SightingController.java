package pl.coderslab.planespotter.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.planespotter.AppUser;
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
                                                @AuthenticationPrincipal AppUser appUser) {

        SightingResponse response = sightingService.create(request, appUser.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

    @GetMapping("/all")
    public ResponseEntity<List<SightingResponse>> findAll(@AuthenticationPrincipal AppUser appUser) {

        return ResponseEntity.ok(sightingService.getSightings(appUser.getUser()));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSighting(@PathVariable Long id,
                                               @AuthenticationPrincipal AppUser appUser) {
        sightingService.deleteSighting(id, appUser.getUser());

        return ResponseEntity.noContent().build();
    }
}


