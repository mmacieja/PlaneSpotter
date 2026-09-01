package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
}
