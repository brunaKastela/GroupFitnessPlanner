package progi.domain;

import javax.persistence.*;

@Entity
@Table(name = "GOALS")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalID;

    @Column(name = "goal_name")
    private String goalName;

    public Goal(String goalName) {
        this.goalName = goalName;
    }

    public Goal() {

    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }


}
