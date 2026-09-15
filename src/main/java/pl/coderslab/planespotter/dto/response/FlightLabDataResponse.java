package pl.coderslab.planespotter.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlightLabDataResponse {

    private List<FlightLabFlightResponse> data;
}
