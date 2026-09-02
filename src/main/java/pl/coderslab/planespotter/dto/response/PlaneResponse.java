package pl.coderslab.planespotter.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaneResponse {

    private Long id;
    private String registration;
    private String icao24;
    private String airline;
}
