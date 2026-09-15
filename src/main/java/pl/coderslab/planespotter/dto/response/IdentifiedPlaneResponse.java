package pl.coderslab.planespotter.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentifiedPlaneResponse {

    private String icao24;
    private String callsign;
    private String origin_country;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Double truetrack;
    private String airline;
    private String departureAirport;
    private String arrivalAirport;

    public IdentifiedPlaneResponse() {
    }

    public IdentifiedPlaneResponse(String icao24, String callsign, String origin_country, Double longitude, Double latitude, Double altitude, Double truetrack) {
        this.icao24 = icao24;
        this.callsign = callsign;
        this.origin_country = origin_country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.truetrack = truetrack;
    }
}
