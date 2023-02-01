package progi.domain.composite;

import progi.domain.Training;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Embeddable
public
class ScheduleID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "trainingID")
    private Training training;
    @Column(name="date")
    private LocalDate date;

    public ScheduleID() {
    }

    public ScheduleID(Training training, LocalDate date) {
        this.training = training;
        this.date = date;
    }



    public LocalDate getDate() {
        return date;
    }

    public String getDateFormatted(){return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));}

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Training getTraining() {
        return this.training;
    }

    @Override
    public String toString() {
        return "ScheduleID{" +
                "trainingid=" + training.getTrainingID() +
                ", date=" + date +
                '}';
    }
}

