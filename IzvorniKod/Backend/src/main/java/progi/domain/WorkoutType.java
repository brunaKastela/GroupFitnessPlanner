package progi.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class WorkoutType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "typeID")
    private Long workoutTypeID;


    private String workoutType;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "workoutID")

    private Set<Workout> workouts;
    public Long getId() {
        return workoutTypeID;
    }

}
