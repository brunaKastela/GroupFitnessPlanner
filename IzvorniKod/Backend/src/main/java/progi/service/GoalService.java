package progi.service;

import org.springframework.stereotype.Service;
import progi.domain.Goal;

import java.util.List;

@Service
public interface GoalService{
    List<Goal> getGoals();

    Goal getGoalByGoalName(String goalName);
}
