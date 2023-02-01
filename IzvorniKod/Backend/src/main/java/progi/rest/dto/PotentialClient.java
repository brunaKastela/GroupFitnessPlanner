package progi.rest.dto;

import progi.domain.Client;
import progi.domain.Goal;
import progi.domain.Role;
import progi.domain.User;

public class PotentialClient {
    private Long clientID;
    private String name;
    private String surname;
    private Goal currentGoal;

    public PotentialClient(Long clientID, String name, String surname, Goal currentGoal) {
        this.clientID = clientID;
        this.name = name;
        this.surname = surname;
        this.currentGoal = currentGoal;
    }

    public PotentialClient(User user) {
        this.clientID = user.getUserID();
        this.name = user.getName();
        this.surname = user.getSurname();
        if (user.getRole().equals(Role.CLIENT)) {
            Client client = (Client) user;
            this.currentGoal = client.getCurrentGoal();
        }
    }

    public Long getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Goal getCurrentGoal() {
        return currentGoal;
    }
}
