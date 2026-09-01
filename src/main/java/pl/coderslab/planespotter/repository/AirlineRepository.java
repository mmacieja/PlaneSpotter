package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
