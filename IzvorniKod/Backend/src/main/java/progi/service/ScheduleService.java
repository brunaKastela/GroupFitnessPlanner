package progi.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import progi.domain.Schedule;

import java.time.LocalDate;


public interface ScheduleService {

    public Schedule getSchedule(long trainingID, LocalDate date);

    void updateTrainingSchedule() throws Exception;
}
