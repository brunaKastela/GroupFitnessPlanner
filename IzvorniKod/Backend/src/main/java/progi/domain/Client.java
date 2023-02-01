package progi.domain;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "CLIENTS")

public class Client extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CLIENT_SCHEDULES",
            joinColumns = @JoinColumn(name = "clientID", referencedColumnName = "userID"),
            inverseJoinColumns = {
                    @JoinColumn(name = "trainingID", referencedColumnName = "trainingID"),
                    @JoinColumn(name = "date", referencedColumnName = "date")
            }
    )
    private Set<Schedule> reservations;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="CURRENT_GOAL")
    private Goal currentGoal;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="NEXT_GOAL")
    private Goal nextGoal;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CLIENT_TRAININGS",
            joinColumns = @JoinColumn(name = "clientID", referencedColumnName = "userID"),
            inverseJoinColumns = @JoinColumn(name = "trainingID", referencedColumnName = "trainingID")
    )
    private Set<Training> assignedTrainings;

    private int hoursAvailable;

    private int totalHours;

    public Client() {

    }

    public Client(String name, String surname, LocalDate dateOfBirth, String email, String username, String password, String contact, Goal currentGoal, Role role, String verificationToken) {
        super(name, surname, dateOfBirth, email, username, password, contact, role, verificationToken);
        this.currentGoal = currentGoal;
        this.hoursAvailable = -1;
    }

//    public Client(String name, String surname, LocalDate dateOfBirth, String email, String username, String password, String contact, Goal currentGoal, Role role, Set<Training> assignedTrainings) {
//        super(name, surname, dateOfBirth, email, username, password, contact, role);
//        this.currentGoal = currentGoal;
//        this.assignedTrainings = assignedTrainings;
//    }

    public Goal getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(Goal currentGoal) {
        this.currentGoal = currentGoal;
    }

    public Goal getNextGoal() {
        return nextGoal;
    }

    public void setNextGoal(Goal nextGoal) {
        this.nextGoal = nextGoal;
    }

    public int getHoursAvailable() {
        return hoursAvailable;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public void setHoursAvailable(int hoursAvailable) {
        this.hoursAvailable = hoursAvailable;
    }

    public Set<Training> getAssignedTrainings() {
        return assignedTrainings;
    }

    public void setAssignedTrainings(Set<Training> assignedTrainings) {
        this.assignedTrainings = assignedTrainings;
    }

    public Set<Schedule> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Schedule> reservations) {
        this.reservations = reservations;
    }
}
