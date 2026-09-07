package pl.coderslab.planespotter.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenSkyStatesResponse {

    private Long time;
    private List<List<Object>> states;
}
