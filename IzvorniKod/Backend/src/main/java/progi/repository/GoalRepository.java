package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import progi.domain.Goal;
import progi.domain.User;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT g FROM Goal g WHERE g.goalName = ?1")
    Goal getGoalByGoalName(String goalName);
}
