package pl.coderslab.planespotter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.planespotter.UnsafeRestTemplate;
import pl.coderslab.planespotter.dto.response.FlightLabDataResponse;
import pl.coderslab.planespotter.dto.response.FlightLabFlightResponse;
import pl.coderslab.planespotter.dto.response.IdentifiedPlaneResponse;
import pl.coderslab.planespotter.entity.Airline;
import pl.coderslab.planespotter.entity.Airport;
import pl.coderslab.planespotter.repository.AirlineRepository;
import pl.coderslab.planespotter.repository.AirportRepository;


@Service
public class FlightLabService {

    @Value("${flightlabs.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;

    public FlightLabService(AirlineRepository airlineRepository, AirportRepository airportRepository) {
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;

        try {
            this.restTemplate = UnsafeRestTemplate.create();
        } catch (Exception exception) {
            throw new RuntimeException("Could not create RestTemplate", exception);
        }
    }

    public FlightLabDataResponse getFlight(String callsign) {

        String url = "https://www.goflightlabs.com/flights-with-call-sign?access_key=" + apiKey + "&callsign=" + callsign;

        return restTemplate.getForObject(url, FlightLabDataResponse.class);
    }

    public IdentifiedPlaneResponse addToSighting(IdentifiedPlaneResponse plane) {
        if (plane.getCallsign() == null || plane.getCallsign().isBlank()) {
            return plane;
        }

        System.out.println("Callsign: " + plane.getCallsign());

        FlightLabDataResponse response;

        try {
            response = getFlight(plane.getCallsign());
        } catch (Exception e) {
            return plane;
        }

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            System.out.println("No data found");
            return null;
        }

        FlightLabFlightResponse flight = response.getData().get(0);
        System.out.println("Found the data");

        Airline airline = airlineRepository.findByIata(flight.getAirline()).orElse(null);

        if (airline != null) {
            plane.setAirline(airline.getName());
        }

        plane.setDepartureAirport(flight.getDepartureAirport());

        Airport departureAirport = airportRepository.findByIata(flight.getDepartureAirport()).orElse(null);

        if (departureAirport != null) {
            plane.setDepartureAirport(departureAirport.getName());
        }

        plane.setArrivalAirport(flight.getArrivalAirport());

        Airport arrivalAirport = airportRepository.findByIata(flight.getArrivalAirport()).orElse(null);

        if (arrivalAirport != null) {
            plane.setArrivalAirport(arrivalAirport.getName());
        }

        return plane;
    }
}
