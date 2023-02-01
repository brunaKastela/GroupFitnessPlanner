package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import progi.domain.Training;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query(value = "SELECT TRAININGS.trainingID as trainingID, TRAININGS.training_Name as trainingName, TRAININGS.duration as duration, " +
            "TRAININGS.training_Rules as trainingRules, TRAININGS.space_available as spaceAvailable, " +
            "COACH_TRAININGS.userid as coachID, USERS.NAME as name, USERS.SURNAME as surname, " +
            "USERS.EMAIL as email, USERS.CONTACT as contact " +
            "FROM USERS " +
            "NATURAL JOIN COACH_TRAININGS " +
            "NATURAL JOIN TRAININGS " +
            "NATURAL JOIN CLIENT_TRAININGS " +
            "WHERE CLIENT_TRAININGS.clientID = ?1", nativeQuery = true)
    List<Training>findClientTrainings(Long id);


    @Query(value = "SELECT * FROM TRAININGS", nativeQuery = true)
    List<Training> findTrainings();


}
