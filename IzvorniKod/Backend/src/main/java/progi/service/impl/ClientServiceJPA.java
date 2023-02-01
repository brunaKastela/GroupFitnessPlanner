package progi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.domain.*;
import progi.repository.*;
import progi.rest.dto.*;
import progi.rest.dto.ClientTraining;
import progi.rest.dto.ScheduleTraining;
import progi.service.ClientService;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientServiceJPA implements ClientService {

    private final TrainingRepository trainingRepo;

    private final ScheduleRepository scheduleRepo;

    private final GoalRepository goalRepo;

    private final CoachRepository coachRepo;

    private final UserRepository userRepo;

    private final ClientRepository clientRepo;


    private final EntityManager em;

    @Autowired
    public ClientServiceJPA(TrainingRepository trainingRepo, ScheduleRepository scheduleRepo, GoalRepository goalRepo, UserRepository userRepo, CoachRepository coachRepo, ClientRepository clientRepo, EntityManager em) {
        this.trainingRepo = trainingRepo;
        this.scheduleRepo = scheduleRepo;
        this.goalRepo = goalRepo;
        this.coachRepo = coachRepo;
        this.clientRepo = clientRepo;
        this.userRepo = userRepo;
        this.em = em;
    }

    @Override
    public boolean bookTraining(ScheduleTraining scheduleTraining) {
        return false;
    }


    @Override
    public List<ClientTraining> getTrainings(UserDataFetching client) throws Exception {
        if (AuthService.userCheck(client.getUserID(), Role.CLIENT)) {
            List<Training> trainingList = new ArrayList<Training>(trainingRepo.findClientTrainings(client.getUserID()));
            List<ClientTraining> clientTrainingList = new ArrayList<ClientTraining>();
            for (int i = 0; i < trainingList.size(); i++) {
                Coach coach = coachRepo.findCoachByTrainingID(trainingList.get(i).getTrainingID());
                clientTrainingList.add(new ClientTraining(trainingList.get(i), coach));
            }
            return clientTrainingList;
        }
        throw new Exception("Traženi korisnik nema dozvolu!");
    }

    @Override
    public Optional<Client> findById(Long userID) {
        return clientRepo.findById(userID);
    }


    @Override
    public Message makeReservation(ClientReservation client) throws Exception {
        if (AuthService.userCheck(client.getUserID(), Role.CLIENT)) {
            Optional<Client> clientTest = findById(client.getUserID());
            if (clientTest.isPresent()) {
                Client clientDB = clientTest.get();

                LocalDate monday = client.getDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate sunday = client.getDate().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                Set<Schedule> betweenDates = clientDB.getReservations().stream().filter(sch -> sch.getScheduleID().getDate().isAfter(monday) && sch.getScheduleID().getDate().isBefore(sunday)).collect(Collectors.toSet());
                int brojRezervacija = (int) (clientDB.getTotalHours() /(4.5*2));
                System.out.println(clientDB.getTotalHours()+" "+brojRezervacija);
                if (clientDB.getHoursAvailable()!=-1 && clientDB.getTotalHours()>=9 && betweenDates.size()==brojRezervacija) {
                    throw new Exception("Za vaš dodijeljeni fond sati nemoguće je rezervirati više od "+brojRezervacija+" treninga u tjednu!");

                }

                Schedule scheduleToAdd = scheduleRepo.getSchedule(client.getTrainingID(), client.getDate());
                Set<Training> trainings = clientDB.getAssignedTrainings();
                if (scheduleToAdd != null) {
                    Set<Schedule> clientSchedules = clientDB.getReservations();
                    if (!clientSchedules.contains(scheduleToAdd)) {
                        if (trainings.contains(scheduleToAdd.getScheduleID().getTraining())) {
                            if (clientDB.getHoursAvailable()>=1) {
                                clientSchedules.add(scheduleToAdd);
                                scheduleToAdd.setSpaceLeft(scheduleToAdd.getSpaceLeft() - 1);
                                clientDB.setHoursAvailable(clientDB.getHoursAvailable() - 2);
                                clientRepo.save(clientDB);
                                scheduleRepo.save(scheduleToAdd);

                                return new Message("Rezervacija uspješno dodana!");
                            }
                            throw new Exception("Potrošili ste svoj mjesečni fond sati!");

                        }
                        throw new Exception("Pokušavate dodati rezervaciju za trening koji vam nije dodijeljen!");
                    }
                    throw new Exception("Nije moguće rezervirati istu rezervaciju više puta!");

                }
                throw new Exception("Rezervacija ne postoji!");

            }
            throw new Exception("Traženi korisnik ne postoji!");
        }
        throw new Exception("Traženi korisnik nema dozvolu!");

    }

    @Override
    public Message cancelReservation(ClientReservation client) throws Exception {
        if (AuthService.userCheck(client.getUserID(), Role.CLIENT)) {
            Optional<Client> clientTest = findById(client.getUserID());
            if (clientTest.isPresent()) {
                Client clientDB = clientTest.get();
                Schedule scheduleToRemove = scheduleRepo.getSchedule(client.getTrainingID(), client.getDate());

                if (scheduleToRemove != null) {

                    Set<Schedule> clientSchedules = clientDB.getReservations();
                    if (clientSchedules.contains(scheduleToRemove)) {
                        clientSchedules.remove(scheduleToRemove);
                        scheduleToRemove.setSpaceLeft(scheduleToRemove.getSpaceLeft() + 1);
                        clientDB.setHoursAvailable(clientDB.getHoursAvailable() + 2);
                        clientRepo.save(clientDB);
                        scheduleRepo.save(scheduleToRemove);
                        return new Message("Rezervacija uspješno uklonjena!");
                    }
                    throw new Exception("Rezervacija za uklanjanje ne postoji!");

                }
                throw new Exception("Rezervacija ne postoji!");

            }
            throw new Exception("Traženi korisnik ne postoji!");
        }
        throw new Exception("Traženi korisnik nema dozvolu!");

    }

    @Override
    public Set<Schedule> getReservations(UserDataFetching client) throws Exception {
        if (AuthService.userCheck(client.getUserID(), Role.CLIENT)) {
            Optional<Client> clientTest = findById(client.getUserID());
            if (clientTest.isPresent()) {
                return clientTest.get().getReservations().stream().filter(sch -> sch.getScheduleID().getDate().isAfter(LocalDate.now())).collect(Collectors.toSet());

            }
            throw new Exception("Traženi korisnik ne postoji!");
        }
        throw new Exception("Traženi korisnik nema dozvolu!");
    }


    @Override
    public List<Schedule> getSchedule(UserTrainingDataFetching client) throws Exception {
        Optional<Client> clientTest = clientRepo.findById(client.getUserID());
        Optional<Training> trainingTest = trainingRepo.findById(client.getTrainingID());
        if (!clientTest.isPresent()) throw new Exception("Navedeni korisnik ne postoji!");
        if (!trainingTest.isPresent()) throw new Exception("Navedeni trening ne postoji!");
        //Coach coachTest = coachRepo.findCoachByTrainingID(client.getTrainingID());
        if (!clientTest.get().getAssignedTrainings().contains(trainingTest.get()))
            throw new Exception("Za navedenog korisnika ne postoji dodijeljeni trening!");

        //ClientTraining training = new ClientTraining(trainingTest, coachTest);
        List<Schedule> schedules = scheduleRepo.findTrainingSchedule(client.getTrainingID());
        //TrainingSchedules trainingSchedules = new TrainingSchedules(schedules);
        if (schedules.size() > 0) return schedules.stream().filter(sch -> sch.getScheduleID().getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
        throw new Exception("Za navedeni trening svi termini za rezervaciju su popunjeni!");


    }

    @Override
    public LoggedUser changeGoal(ClientGoal clientGoal) throws Exception {

        Optional<Client> clientTest = clientRepo.findById(clientGoal.getUserID());

        if (clientTest.isPresent()) {
            Client clientDB = clientTest.get();
            Goal goal = goalRepo.getGoalByGoalName(clientGoal.getGoalName());
            //Optional<Goal> goalTest = goalRepo.findById(clientGoal.getGoalName());

            if (goal != null) {

                clientDB.setNextGoal(goal);
                clientRepo.save(clientDB);

                Optional<User> userResult = userRepo.findById(clientDB.getUserID());
                if (userResult.isPresent()) {

                    return new LoggedUser(userResult.get());
                }
            }
            throw new Exception("Navedeni cilj ne postoji!");

        }
            throw new Exception("Navedeni korisnik ne postoji!");


    }

    @Override
    public boolean checkWeek() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        long daysUntilEndOfMonth = today.until(lastDayOfMonth, ChronoUnit.DAYS);
        return daysUntilEndOfMonth <= 26;
    }

    @Override
    public void resetClientsWithNextGoal() {
        clientRepo.deleteClientCoach();
        clientRepo.deleteClientTrainings();
        clientRepo.updateGoals();
    }
}