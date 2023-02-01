package progi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import progi.domain.Schedule;
import progi.domain.Training;
import progi.repository.ScheduleRepository;
import progi.repository.TrainingRepository;
import progi.rest.dto.Message;
import progi.rest.dto.TrainingsOwnSchedule;
import progi.service.ScheduleService;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@Configuration
public class ScheduleServiceJPA implements ScheduleService {

    private final ScheduleRepository scheduleRepo;

    private final TrainingRepository trainingRepo;


    @Autowired
    ScheduleServiceJPA(ScheduleRepository scheduleRepo, TrainingRepository trainingRepo) {

        this.scheduleRepo = scheduleRepo;
        this.trainingRepo = trainingRepo;
    }
    @Override
    public Schedule getSchedule(long trainingID, LocalDate date) {
        return scheduleRepo.getSchedule(trainingID,date);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * *")
    public void updateTrainingSchedule() throws Exception{
        System.out.println("EXECUTED");
        List<TrainingsOwnSchedule> trainingsScheduleList = new ArrayList<>();

        for(Schedule schedule : scheduleRepo.findSchedules()) {
            TrainingsOwnSchedule trainingsOwnSchedule = new TrainingsOwnSchedule(schedule.getScheduleID().getTraining().getTrainingID(), schedule.getScheduleID().getDate().getDayOfWeek().getValue(), schedule.getTimeOfDay());
            trainingsScheduleList.add(trainingsOwnSchedule);
        }

        List<TrainingsOwnSchedule> distinctTrainingsOwnScheduleList = trainingsScheduleList.stream().distinct().collect(Collectors.toList());

        scheduleRepo.deleteReservations();
        scheduleRepo.deleteSchedules();

        createSchedules(distinctTrainingsOwnScheduleList);
    }

    public void createSchedules(List<TrainingsOwnSchedule> trainingsScheduleList) throws Exception{

        try {

            List<Training> trainingList = trainingRepo.findTrainings();
            LocalDate current_date = LocalDate.now();
            LocalDate current_date_adj = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LocalDate startDate;
            if (current_date.isAfter(current_date_adj))
                 startDate = current_date;
            else
                startDate = current_date_adj;
            LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
            List<LocalDate> dates = startDate.datesUntil(endDate).toList();

            Set<Schedule> schedules = new HashSet<>();

            System.out.print(trainingList);
            System.out.println();
            System.out.print(trainingsScheduleList);

            for(Training training : trainingList) {
                System.out.println(training.getTrainingID() + " " + training.getTrainingName());
                for (TrainingsOwnSchedule trainingsSchedule : trainingsScheduleList) {
                    System.out.println(trainingsSchedule.getTrainingID() + " " + trainingsSchedule.getWeekDay() + " " + trainingsSchedule.getTimeOfDay());
                    if(Objects.equals(trainingsSchedule.getTrainingID(), training.getTrainingID())) {
                        for (LocalDate date : dates) {
                            if (date.getDayOfWeek().getValue() == trainingsSchedule.getWeekDay()) {
                                Schedule schedule = new Schedule(training, date, trainingsSchedule.getTimeOfDay(), training.getSpaceAvailable());
                                System.out.println(schedule.getScheduleID().getTraining().getTrainingID() + " " + schedule.getScheduleID().getDate() + " " + schedule.getTimeOfDay() + " " + schedule.getSpaceAvailable());
                                schedules.add(schedule);
                            }
                        }
                    }
                }
            }

            schedules.forEach(s -> {
                System.out.println(s.toString());
                scheduleRepo.saveEntity(s.getScheduleID().getDate(), s.getSpaceAvailable(), s.getTimeOfDay(), s.getScheduleID().getTraining().getTrainingID());
            });

            new Message("OK");
        } catch (Exception e) {
            throw new Exception("Greška u izradi termina treninga!");
        }
    }
}
