package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.Airline;
import pl.coderslab.planespotter.entity.Airport;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByIata(String iata);

    Optional<Airport> findByName(String name);

}
