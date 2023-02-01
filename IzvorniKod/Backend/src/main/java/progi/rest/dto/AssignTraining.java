package progi.rest.dto;


public class AssignTraining {


    private long clientID;
    private long trainingID;
    private long coachID;

    public AssignTraining(long clientID, long trainingID, long coachID) {
        this.clientID = clientID;
        this.trainingID = trainingID;
        this.coachID = coachID;
    }

    public long getClientID() {
        return (clientID);
    }

    public long getTrainingID() {
        return (trainingID);
    }

    public long getCoachID() {
        return (coachID);
    }

}
