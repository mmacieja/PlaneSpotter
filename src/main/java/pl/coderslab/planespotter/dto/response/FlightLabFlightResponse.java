package pl.coderslab.planespotter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightLabFlightResponse {

    @JsonProperty("icao_24bit")
    private String icao24;
    @JsonProperty("callsign")
    private String callsign;
    @JsonProperty("origin_airport_iata")
    private String departureAirport;
    @JsonProperty("destination_airport_iata")
    private String arrivalAirport;
    @JsonProperty("airline_iata")
    private String airline;
}
