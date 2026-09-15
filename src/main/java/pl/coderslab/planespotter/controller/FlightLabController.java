package pl.coderslab.planespotter.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.planespotter.dto.response.FlightLabDataResponse;
import pl.coderslab.planespotter.service.FlightLabService;

@RestController
@RequestMapping("/flightlabs")
public class FlightLabController {

    private final FlightLabService service;

    public FlightLabController(FlightLabService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<FlightLabDataResponse> test(@RequestParam String callsign) {

        return ResponseEntity.ok(service.getFlight(callsign));
    }
}
