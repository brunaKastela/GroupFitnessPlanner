package progi.rest.dto;

import java.util.Objects;

public class TrainingsOwnSchedule {

    private Long trainingID;

    private int weekDay;

    private int timeOfDay;

    public TrainingsOwnSchedule(Long trainingID, int weekDay, int timeOfDay) {
        this.trainingID = trainingID;
        this.weekDay = weekDay;
        this.timeOfDay = timeOfDay;
    }

    public Long getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(Long trainingID) {
        this.trainingID = trainingID;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(int timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingsOwnSchedule that = (TrainingsOwnSchedule) o;
        return weekDay == that.weekDay && timeOfDay == that.timeOfDay && trainingID.equals(that.trainingID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingID, weekDay, timeOfDay);
    }
}
