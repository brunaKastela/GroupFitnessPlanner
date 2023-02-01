package progi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "WORKOUTS")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workoutID;

    @NotNull
    @Column(name="WORKOUT_NAME")
    private String workoutName;

    @ManyToOne
    @JoinColumn(name = "workoutTypeID", referencedColumnName = "typeID")
    private WorkoutType workoutType;


    public Workout(String workoutName, WorkoutType workoutType) {

        this.workoutName = workoutName;
        this.workoutType = workoutType;
    }

    public Workout() {

    }

    public Long getWorkoutID() {
        return workoutID;
    }


    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public WorkoutType getWorkOutType() {
        return workoutType;
    }

    public void setWorkOutType(WorkoutType workOutType) {
        this.workoutType = workOutType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return workoutID == workout.workoutID;
    }

//    @Override
//    public String toString() {
//        return "Workout{" +
//                "workoutID=" + workoutID +
//                ", workoutName='" + workoutName + '\'' +
//                ", workoutType=" + workoutType +
//                '}';
//    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutID);
    }
}
