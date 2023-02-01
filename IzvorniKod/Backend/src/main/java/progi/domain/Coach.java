package progi.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
@Entity
@Table(name = "COACHES")
public class Coach extends User{

    public Set<Client> getClients() {
        return clients;
    }

    @OneToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinTable(
            name = "COACH_CLIENTS",
            joinColumns = @JoinColumn(name = "coachID", referencedColumnName = "userID"),
            inverseJoinColumns = @JoinColumn(name = "clientID", referencedColumnName = "userID")
    )
    private Set<Client> clients;

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "COACH_TRAININGS",
            inverseJoinColumns =  @JoinColumn(name = "trainingID", referencedColumnName = "trainingID"),
            joinColumns = @JoinColumn(name = "userID", referencedColumnName = "userID")
    )
    private Set<Training> trainings;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    private boolean verified;

    public Coach(String name, String surname, LocalDate dateOfBirth, String email, String username, String password, String contact, Role role, String verificationToken) {
        super(name, surname, dateOfBirth, email, username, password, contact, role, verificationToken);
    }

//    public Coach(String name, String surname, String username, String email, String contact) {
//        super(name, surname, username, email, contact);
//    }

    public Coach() {

    }


}
