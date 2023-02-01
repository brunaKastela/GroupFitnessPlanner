package progi.rest.dto;

import progi.domain.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoggedUser {
    private Long userID;
    private String username;
    private String name;
    private String surname;
    private String dateOfBirth;
    private String email;
    private String contact;
    private Role role;
    private Goal currentGoal;

    private Goal nextGoal;
    private boolean verified;

    public LoggedUser(User user) {
        this.userID = user.getUserID();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dateOfBirth = user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.email = user.getEmail();
        this.contact = user.getContact();
        this.role = user.getRole();
        if (user.getRole().equals(Role.CLIENT)) {
            Client client = (Client) user;
            this.currentGoal = client.getCurrentGoal();
            if(client.getNextGoal() != null) {
                this.nextGoal = client.getNextGoal();
            }
        }
        else if (user.getRole().equals(Role.COACH)) {
            Coach coach = (Coach) user;
            this.verified = coach.isVerified();
        }
    }


    public Long getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public Role getRole() {
        return role;
    }

    public Goal getCurrentGoal() {
        return currentGoal;
    }
    public boolean isVerified() {
        return verified;
    }

    public Goal getNextGoal() {
        return nextGoal;
    }

    public void setNextGoal(Goal nextGoal) {
        this.nextGoal = nextGoal;
    }
}
