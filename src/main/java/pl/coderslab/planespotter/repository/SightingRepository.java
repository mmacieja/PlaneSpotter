package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.Sighting;
import pl.coderslab.planespotter.entity.User;

import java.util.Arrays;
import java.util.List;

public interface SightingRepository extends JpaRepository<Sighting, Long> {

    List<Sighting> findByUser(User user);
}
