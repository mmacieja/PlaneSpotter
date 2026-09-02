package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.Plane;

public interface PlaneRepository extends JpaRepository<Plane, Long> {

    boolean existsByIcao24(String icao24);
    boolean existsByIcao24AndIdNot(String icao24, Long id);

}
