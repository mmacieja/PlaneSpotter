package pl.coderslab.planespotter.dto.response;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SightingResponse {


    private Long id;

    private LocalDateTime time;

    private Double latitude;
    private Double longitude;

    private String departureAirport;
    private String arrivalAirport;

    private String icao24;
    private String callsign;
    private String username;
}
