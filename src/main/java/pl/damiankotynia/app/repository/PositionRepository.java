package pl.damiankotynia.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.damiankotynia.app.model.Position;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> getByUser(String user);
}
