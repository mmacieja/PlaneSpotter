package pl.coderslab.planespotter.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.planespotter.dto.request.PlaneRequest;
import pl.coderslab.planespotter.dto.response.PlaneResponse;
import pl.coderslab.planespotter.service.PlaneService;

import java.util.List;

@RestController
@RequestMapping("/plane")
public class PlaneController {

    private final PlaneService planeService;

    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @PostMapping
    public ResponseEntity<PlaneResponse> createPlane(@Valid @RequestBody PlaneRequest planeRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planeService.create(planeRequest));
    }

    @GetMapping
    public ResponseEntity<List<PlaneResponse>> findAllPlanes(){

        return ResponseEntity.ok(planeService.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaneResponse> findPlane(@PathVariable Long id){

        return ResponseEntity.ok(planeService.findById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlane(@PathVariable Long id){

        planeService.delete(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaneResponse> updatePlane(@PathVariable Long id,
            @Valid @RequestBody PlaneRequest planeRequest){

        return ResponseEntity.ok(planeService.update(id, planeRequest));

    }
}
