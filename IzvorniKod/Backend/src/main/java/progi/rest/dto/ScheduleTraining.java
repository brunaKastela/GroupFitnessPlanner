package progi.rest.dto;


import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ScheduleTraining {
    @NotEmpty(message = "Naziv treninga ne može biti prazan!")
    private final String trainingName;

    @NotEmpty(message = "Pravila treninga ne mogu biti prazna!")

    private final String trainingRules;
    @NotNull(message = "Dostupna mjesta na treningu ne mogu biti prazna!")

    private final int spaceAvailable;

    @NotEmpty(message = "Trening ne može biti bez vježbi!")
    private final int[] workouts;
    @NotNull(message = "Trening ne može biti bez trenera!")
    private final Long coachID;
    @NotEmpty(message = "Trening ne može biti bez termina!")
    private final int[][] schedule;


    public ScheduleTraining(String trainingName, String trainingRules, int spaceAvailable, int[] workouts, Long coachID, int[][] schedule) {
        this.trainingName = trainingName;
        this.trainingRules = trainingRules;
        this.spaceAvailable = spaceAvailable;
        this.workouts = workouts;
        this.coachID = coachID;
        this.schedule = schedule;
    }



    public String getTrainingName() {
        return trainingName;
    }


    public String getTrainingRules() {
        return trainingRules;
    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public int[] getWorkouts() {
        return workouts;
    }

    public Long getCoachID() {
        return coachID;
    }

    public int[][] getSchedule() {
        return schedule;
    }
}
