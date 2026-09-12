package pl.coderslab.planespotter.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaneRequest {

    @NotBlank(message = "ICAO24 cannot be blank")
    @Size(min = 6, max = 6, message = "ICAO24 must have 6 characters")
    private String icao24;

    @NotBlank(message = "Callsign cannot be blank")
    private String callsign;
    private Long airlineId;
}
