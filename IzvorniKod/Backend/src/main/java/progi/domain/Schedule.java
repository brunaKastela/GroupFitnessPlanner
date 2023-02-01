package progi.domain;

import progi.domain.composite.ScheduleID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "SCHEDULES")
@AssociationOverride(name = "scheduleID.training", joinColumns = @JoinColumn(name="trainingID"))
public class Schedule implements Serializable {

    @EmbeddedId
    private ScheduleID scheduleID;

    @NotNull
    private int timeOfDay;

    @NotNull
    private int spaceAvailable;





    public Schedule() {

    }

    public Schedule(Training training, LocalDate date, int timeOfDay, int spaceAvailable) {
        this.scheduleID = new ScheduleID(training, date);
        this.timeOfDay = timeOfDay;
        this.spaceAvailable = spaceAvailable;
    }

    public ScheduleID getScheduleID() {
        return scheduleID;
    }

//    public void setScheduleID(Training training, LocalDate date) {
//
//        this.scheduleID.setDate(date);
//        this.scheduleID.setTraining(training);
//    }


    public int getSpaceLeft() {
        return spaceAvailable;
    }

    public void setSpaceLeft(int spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }

    public void setScheduleID(ScheduleID scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(int timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public void setSpaceAvailable(int spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleID=" + scheduleID +
                ", timeOfDay=" + timeOfDay +
                ", spaceAvailable=" + spaceAvailable +
                '}';
    }
}
