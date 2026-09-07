package pl.coderslab.planespotter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.planespotter.UnsafeRestTemplate;
import pl.coderslab.planespotter.dto.response.OpenSkyPlaneResponse;
import pl.coderslab.planespotter.dto.response.OpenSkyStatesResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenSkyService {
    private final RestTemplate restTemplate;

    public OpenSkyService() {

        try {
            this.restTemplate = UnsafeRestTemplate.create();
        }catch(Exception exception){
            throw new RuntimeException("Could not create RestTemplate", exception);
        }
    }


    public List<OpenSkyPlaneResponse> getPlanes(double la, double lo){

        double margin = 1;
        double lamin = la - margin;
        double lamax = la + margin;
        double lomin = lo - margin;
        double lomax = lo + margin;

        String url = "https://opensky-network.org/api/states/all"
                +"?lamin=" + lamin +
                "&lomin=" + lomin +
                "&lamax=" + lamax +
                "&lomax=" + lomax;


        OpenSkyStatesResponse response = restTemplate.getForObject(url, OpenSkyStatesResponse.class);

        List<OpenSkyPlaneResponse> planes = new ArrayList<>();

        if (response == null || response.getStates() == null){
            return planes;
        }

        for (List<Object> state : response.getStates()){

            String icao24 = state.get(0).toString();
            String callsign = state.get(1).toString();
            String origin_country = state.get(2).toString();
            Double longitude = getDouble(state,5);
            Double latitude = getDouble(state,6);
            Double altitude = getDouble(state,7);
            Double truetrack = getDouble(state,10);

            planes.add(new OpenSkyPlaneResponse(icao24, callsign, origin_country, longitude, latitude, altitude, truetrack ));
        }

        return planes;
    }

    private Double getDouble(List<Object> state, int index){

        Object value = state.get(index);

        if (value == null){
            return null;
        }

        return ((Number) value).doubleValue();
    }
}
