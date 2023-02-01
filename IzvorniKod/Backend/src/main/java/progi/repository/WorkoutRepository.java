package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.domain.Training;
import progi.domain.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

}
