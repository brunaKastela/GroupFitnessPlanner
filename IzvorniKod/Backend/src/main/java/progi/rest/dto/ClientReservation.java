package progi.rest.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClientReservation {

    private long userID;
    private long trainingID;
    private String date;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ClientReservation(long userID, long trainingID, String date) {
        this.userID = userID;
        this.trainingID = trainingID;
        this.date = date;
    }

    public long getUserID() {
        return userID;
    }

    public long getTrainingID() {
        return trainingID;
    }

    public LocalDate getDate() {
        return LocalDate.parse(date, formatter);
    }
}
