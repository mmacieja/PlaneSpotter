package pl.coderslab.planespotter.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SightingRequest {


    @NotNull(message = "ICAO24 is required")
    @Size(min = 6, max = 6, message = "ICAO24 must have 6 characters")
    private String icao24;

    @NotNull(message = "Callsign is required")
    private String callsign;

    @NotNull(message = "Latitude id is required")
    private Double latitude;

    @NotNull(message = "Longitude id is required")
    private Double longitude;

    private String airline;
    private String departureAirport;
    private String arrivalAirport;
}
