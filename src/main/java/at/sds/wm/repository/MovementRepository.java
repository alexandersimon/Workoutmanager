package at.sds.wm.repository;

import at.sds.wm.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovementRepository
        extends JpaRepository<Movement, Long> {

    Optional<Movement> findByNameIgnoreCase(String name);
}
