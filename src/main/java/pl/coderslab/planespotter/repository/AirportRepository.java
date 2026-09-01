package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
