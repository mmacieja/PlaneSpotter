package pl.coderslab.planespotter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.planespotter.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByUsernameAndIdNot(String username, Long id);
}
