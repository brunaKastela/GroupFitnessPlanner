package progi.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import progi.domain.composite.ScheduleID;
import progi.rest.dto.ClientTraining;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@NamedNativeQuery(name = "Training.findClientTrainings",
        query = "SELECT TRAININGS.trainingID as trainingID, TRAININGS.training_Name as trainingName, TRAININGS.duration as duration, " +
                "TRAININGS.training_Rules as trainingRules, TRAININGS.space_available as spaceAvailable, " +
                "COACH_TRAININGS.userid as coachID, USERS.NAME as name, USERS.SURNAME as surname, " +
                "USERS.EMAIL as email, USERS.CONTACT as contact " +
                "FROM USERS " +
                "NATURAL JOIN COACH_TRAININGS " +
                "NATURAL JOIN TRAININGS " +
                "NATURAL JOIN CLIENT_TRAININGS " +
                "WHERE CLIENT_TRAININGS.clientID = :id",
                resultSetMapping = "Mapping.ClientTraining")

@SqlResultSetMapping(name = "Mapping.ClientTraining",
        classes = @ConstructorResult(targetClass = ClientTraining.class,
                columns = {
                        @ColumnResult(name = "trainingID", type = long.class),
                        @ColumnResult(name = "trainingName"),
                        @ColumnResult(name = "duration"),
                        @ColumnResult(name = "trainingRules"),
                        @ColumnResult(name = "spaceAvailable"),
                        @ColumnResult(name = "coachID", type = long.class),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "surname"),
                        @ColumnResult(name = "email"),
                        @ColumnResult(name = "contact")}
                ))

@Entity
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingID;

    @NotNull
    private String trainingName;

    @NotNull
    private int duration;

    @NotNull
    @Size(min = 0, max = 500)
    private String trainingRules;

    @NotNull
    private int spaceAvailable;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name="TRAINING_WORKOUTS",
            joinColumns=
            @JoinColumn(name="trainingID", referencedColumnName="trainingID"),
            inverseJoinColumns=
            @JoinColumn(name="workoutID", referencedColumnName="workoutID")
    )
    private Set<Workout> workouts;



    public Training(String trainingName,int spaceAvailable, String trainingRules, Set<Workout> workouts) {
        this.trainingName = trainingName;
        this.duration = 2;
        this.spaceAvailable = spaceAvailable;
        this.trainingRules = trainingRules;
        this.workouts = workouts;
    }

    public Training() {

    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public Long getTrainingID() {
        return trainingID;
    }


    public String getTrainingName() {
        return trainingName;
    }

    public int getDuration() {
        return duration;
    }

    public String getTrainingRules() {
        return trainingRules;
    }

    public void setTrainingID(Long trainingID) {
        this.trainingID = trainingID;
    }

    public void setSpaceAvailable(int spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }


    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTrainingRules(String trainingRules) {
        this.trainingRules = trainingRules;
    }

    public Set<Workout> getWorkouts() {
        return workouts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return trainingID.equals(training.trainingID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingID);
    }
}
