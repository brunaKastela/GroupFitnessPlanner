package progi.service.impl;

import org.springframework.stereotype.Service;
import progi.domain.Goal;
import progi.repository.GoalRepository;
import progi.service.GoalService;

import java.util.List;

@Service
public class GoalServiceJPA implements GoalService {
    private GoalRepository goalRepo;

    public GoalServiceJPA(GoalRepository goalRepo) {

        this.goalRepo = goalRepo;
    }


    @Override
    public List<Goal> getGoals() {
        return goalRepo.findAll();
    }

    @Override
    public Goal getGoalByGoalName(String goalName) {

        return goalRepo.getGoalByGoalName(goalName);
    }
}
