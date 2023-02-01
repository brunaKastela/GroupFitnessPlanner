package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import progi.domain.Client;
import progi.domain.User;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CLIENT_SCHEDULES WHERE trainingid IN ?1", nativeQuery = true)
    void deleteFromClientSchedulesByTrainingIDs(List<Long> trainingIDs);

    @Query(value = "SELECT clientid FROM CLIENT_TRAININGS WHERE trainingid IN ?1", nativeQuery = true)
    List<Long> findClientsByTrainingIDs(List<Long> trainingIDs);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CLIENT_TRAININGS WHERE trainingid IN ?1", nativeQuery = true)
    void deleteFromClientTrainingsByTrainingIDs(List<Long> collect);


    @Modifying
    @Transactional
    @Query(value = "UPDATE SCHEDULES SET SPACE_AVAILABLE = SPACE_AVAILABLE + 1 WHERE TRAININGID IN (SELECT TRAININGID FROM CLIENT_SCHEDULES WHERE CLIENTID = ?1 ) AND DATE IN (SELECT DATE FROM CLIENT_SCHEDULES WHERE CLIENTID = ?1 )", nativeQuery = true)
    void deleteReservations(Long userID);


    @Modifying
    @Transactional
    @Query(value = "UPDATE CLIENTS SET CURRENT_GOAL = NEXT_GOAL, NEXT_GOAL = NULL WHERE NEXT_GOAL IS NOT NULL ", nativeQuery = true)
    void updateGoals();

    @Query(value = "SELECT * FROM CLIENTS NATURAL JOIN USERS WHERE CLIENTS.userID NOT IN (SELECT clientID FROM CLIENT_TRAININGS )", nativeQuery = true)
    List<Client> findClientsWithoutTrainings();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM COACH_CLIENTS WHERE CLIENTID IN (SELECT USERID FROM CLIENTS WHERE NEXT_GOAL IS NOT NULL AND NEXT_GOAL != CURRENT_GOAL ) ", nativeQuery = true)
    void deleteClientCoach();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CLIENT_TRAININGS WHERE CLIENTID IN (SELECT USERID FROM CLIENTS WHERE NEXT_GOAL IS NOT NULL AND NEXT_GOAL != CURRENT_GOAL)", nativeQuery = true)
    void deleteClientTrainings();
}
