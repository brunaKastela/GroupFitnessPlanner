package progi.service;

import progi.domain.Client;
import progi.domain.Goal;
import progi.domain.Schedule;
import progi.rest.dto.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ClientService {

    boolean bookTraining(ScheduleTraining scheduleTraining);

    List<ClientTraining> getTrainings(UserDataFetching client) throws Exception;

    Optional<Client> findById(Long userID);

    Message makeReservation(ClientReservation client) throws Exception;

    Message cancelReservation(ClientReservation client) throws Exception;

    Set<Schedule> getReservations(UserDataFetching client) throws Exception;

    List<Schedule> getSchedule(UserTrainingDataFetching client) throws Exception;

    LoggedUser changeGoal(ClientGoal clientGoal) throws Exception;

    boolean checkWeek();

    void resetClientsWithNextGoal();
}
