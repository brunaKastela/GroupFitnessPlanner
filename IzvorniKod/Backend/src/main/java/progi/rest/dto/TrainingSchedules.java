package progi.rest.dto;

import progi.domain.Schedule;
import progi.domain.Training;
import progi.domain.composite.ScheduleID;

import java.util.List;

public class TrainingSchedules {


    private ScheduleID scheduleID;



    public TrainingSchedules(ScheduleID scheduleID) {
        this.scheduleID = scheduleID;
    }

    public ScheduleID getScheduleID() {
        return scheduleID;
    }
}
