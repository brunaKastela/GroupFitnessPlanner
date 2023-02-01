package progi.service;


import org.springframework.stereotype.Service;

import progi.domain.Training;
import progi.domain.Workout;
import progi.rest.dto.*;

import java.util.List;

@Service
public interface CoachService {

    Message createTraining(ScheduleTraining scheduleTraining) throws Exception;

    Message assignTraining(AssignTraining assignTraining) throws Exception;

    Message getHoursAvailable(UserHoursDataFetching userHoursDataFetching) throws Exception;

    List<Workout> getWorkouts(UserDataFetching coach) throws Exception;

    List<PotentialClient> getPotentialClients(UserDataFetching user) throws Exception;

    List<Training> getTrainings(UserDataFetching coach) throws Exception;

    List<Integer> getAvailableSchedule(UserScheduleDataFetching coach) throws Exception;


}
