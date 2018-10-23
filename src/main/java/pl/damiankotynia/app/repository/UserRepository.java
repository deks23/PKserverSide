package pl.damiankotynia.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.damiankotynia.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    List<User> findByName(String name);
}
