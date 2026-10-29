package at.sds.wm.repository;

import at.sds.wm.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
//@Transactional
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

}
