package progi.rest.dto;

import progi.domain.Goal;
import progi.domain.Role;
import progi.domain.User;

public class ClientRegistration extends UserRegistration {
    private String goal;

    public ClientRegistration(String name, String surname, String dateOfBirth, String contact, String email, String username, String password, String goal) throws Exception{
        super(name, surname, dateOfBirth, contact, email, username, password);
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
    }

    @Override
    public String toString() {
        return "ClientRegistration{" +
                "goal='" + goal + '\'' +
                '}';
    }
}
