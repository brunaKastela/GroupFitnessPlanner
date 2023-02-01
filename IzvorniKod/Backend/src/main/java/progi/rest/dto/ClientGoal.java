package progi.rest.dto;

public class ClientGoal {

    private Long userID;

    private String goalName;

    public ClientGoal(Long userID, String goalName) {
        this.userID = userID;
        this.goalName = goalName;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalID(String goalName) {
        this.goalName = goalName;
    }
}
