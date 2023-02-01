package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import progi.domain.Schedule;
import progi.rest.dto.TrainingsOwnSchedule;

import javax.transaction.Transactional;
import java.time.LocalDate;

import java.util.List;
import java.util.Set;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM SCHEDULES WHERE SCHEDULES.trainingID = ?1 AND SCHEDULES.date = ?2", nativeQuery = true)
    Schedule getSchedule(long trainingID, LocalDate date);

    @Query(value = "SELECT * FROM SCHEDULES WHERE SCHEDULES.trainingID = ?1 AND SCHEDULES.space_available!=0", nativeQuery = true)
    List<Schedule> findTrainingSchedule(Long trainingID);
    @Query(value = "SELECT SCHEDULES.time_of_day FROM SCHEDULES WHERE SCHEDULES.date = ?1", nativeQuery = true)
    List<Integer> getAvailableSchedule(LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SCHEDULES WHERE SCHEDULES.trainingid IN ?1", nativeQuery = true)
    void deleteByTrainingIDs(List<Long> trainingIDs);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SCHEDULES", nativeQuery = true)
    void deleteSchedules();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CLIENT_SCHEDULES", nativeQuery = true)
    void deleteReservations();

    @Modifying
    @Transactional
    @Query(value = "SELECT DISTINCT TRAININGID, DAYOFWEEK(DATE) - 1 as WEEKDAY, TIME_OF_DAY as timeOfDay FROM SCHEDULES WHERE TRAININGID = ?1 ", nativeQuery = true)
    Schedule getTrainingsSchedule(Long trainingID);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM SCHEDULES", nativeQuery = true)
    List<Schedule> findSchedules();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO SCHEDULES (DATE, SPACE_AVAILABLE, TIME_OF_DAY, TRAININGID) VALUES (:date, :space_available, :time_of_day, :trainingID) ", nativeQuery = true)
    void saveEntity(LocalDate date, int space_available, int time_of_day, Long trainingID);
}
