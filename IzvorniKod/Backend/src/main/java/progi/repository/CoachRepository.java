package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import progi.domain.Coach;

import javax.transaction.Transactional;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Query(value = "SELECT * " +
            "FROM USERS " +
            "NATURAL JOIN COACHES " +
            "NATURAL JOIN COACH_TRAININGS " +
            "WHERE COACH_TRAININGS.TRAININGID = ?1 ", nativeQuery = true)
    Coach findCoachByTrainingID(Long trainingID);

    @Query(value = "DELETE FROM COACH_CLIENTS WHERE clientID = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteClientFromCoachClientsByClientID(Long userID);
}
