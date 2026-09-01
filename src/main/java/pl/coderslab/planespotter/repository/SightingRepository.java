package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.Sighting;

public interface SightingRepository extends JpaRepository<Sighting, Long> {
}
