package progi.rest.dto;

import org.springframework.beans.factory.annotation.Autowired;
import progi.domain.Coach;
import progi.domain.Training;
import progi.domain.Workout;
import progi.repository.ScheduleRepository;
import progi.repository.TrainingRepository;
import progi.service.impl.ClientServiceJPA;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class ClientTraining {

    private Training training;

    private Coach coach;

    public ClientTraining(Training training, Coach coach) {
        this.training = training;
        this.coach = coach;
    }

    public ClientTraining() {
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}
