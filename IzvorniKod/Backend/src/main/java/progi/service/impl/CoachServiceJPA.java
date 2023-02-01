package progi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.domain.*;
import progi.repository.*;
import progi.rest.dto.*;
import progi.service.CoachService;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoachServiceJPA implements CoachService {

    private final TrainingRepository trainingRepo;

    private final WorkoutRepository workoutRepo;

    private final ScheduleRepository scheduleRepo;

    private final ClientRepository clientRepo;

    private final CoachRepository coachRepo;


    private final UserRepository userRepo;


    private final EntityManager em;


    @Autowired
    public CoachServiceJPA(TrainingRepository trainingRepo, ScheduleRepository scheduleRepo, UserRepository userRepo, WorkoutRepository workoutRepo, ClientRepository clientRepo, CoachRepository coachRepo, EntityManager em) {
        this.trainingRepo = trainingRepo;
        this.scheduleRepo = scheduleRepo;
        this.workoutRepo = workoutRepo;
        this.clientRepo = clientRepo;
        this.userRepo = userRepo;
        this.coachRepo = coachRepo;
        this.em = em;
    }

    @Override
    public Message createTraining(ScheduleTraining scheduleTraining) throws Exception {


            Set<Workout> workouts = new HashSet<>();
            for (int work : scheduleTraining.getWorkouts()) {
                Workout workout = workoutRepo.findById((long) work).orElseThrow(EntityNotFoundException::new);
                workouts.add(workout);
            }

            Coach coach = coachRepo.findById(scheduleTraining.getCoachID()).orElseThrow(EntityNotFoundException::new);

            if(!coach.isVerified()) {
                throw new IllegalAccessException("Još niste verificirani od strane admina!");
            }

            Training training = new Training(scheduleTraining.getTrainingName(), scheduleTraining.getSpaceAvailable(), scheduleTraining.getTrainingRules(), workouts);
            trainingRepo.save(training);

            coach.getTrainings().add(training);
            coachRepo.save(coach);

            LocalDate startDate = java.time.LocalDate.now();
            LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
            List<LocalDate> dates = startDate.datesUntil(endDate).collect(Collectors.toList());

            Set<Schedule> schedules = new HashSet<>();

            for (LocalDate date : dates) {
                for (int[] str : scheduleTraining.getSchedule()) {

                    if (date.getDayOfWeek().getValue() == str[0]) {
                        Schedule schedule = new Schedule(training, date, str[1], scheduleTraining.getSpaceAvailable());
                        schedules.add(schedule);
                        break;
                    }
                }
            }
            //trainingRepo.save(training);
            scheduleRepo.saveAll(schedules);
            return new Message("OK");
    }

    @Override
    public Message assignTraining(AssignTraining assignTraining) throws Exception {
        if (AuthService.userCheck(assignTraining.getCoachID(), Role.COACH)) {

            try {
                Client client = em.getReference(Client.class, assignTraining.getClientID());
                Training training = em.getReference(Training.class, assignTraining.getTrainingID());
                Coach coach = em.getReference(Coach.class, assignTraining.getCoachID());
                Set<Training> assignedTrainings = client.getAssignedTrainings();

                if(!coach.isVerified()) {
                    throw new Exception("Još niste verificirani od strane admina!");
                }

                if (!assignedTrainings.contains(training)) {
                    assignedTrainings.add(training);
                    client.setAssignedTrainings(assignedTrainings);
                    clientRepo.save(client);
                    coach.getClients().add(client);
                    coachRepo.save(coach);
                    return new Message("OK");
                }
                throw new Exception("Nije moguće dodijeliti isti trening više puta!");
            }
            catch (EntityNotFoundException e) {
                throw new Exception("Trening i/ili klijent i/ili trener s navedenim id-om ne postoji u bazi podataka!");
            }
            catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        throw new Exception("Traženi korisnik nema dozvolu!");
    }


    @Override
    public List<Workout> getWorkouts(UserDataFetching coach) throws Exception {
        if (AuthService.userCheck(coach.getUserID(),Role.COACH)) {
            return workoutRepo.findAll();

        }
        throw new Exception("Traženi korisnik nema dozvolu!");

    }

    @Override
    public List<PotentialClient> getPotentialClients(UserDataFetching coach) throws Exception {
        if (AuthService.userCheck(coach.getUserID(),Role.COACH)) {

            return clientRepo.findClientsWithoutTrainings().stream().map(client -> new PotentialClient(client.getUserID(), client.getName(), client.getSurname(), client.getCurrentGoal())).collect(Collectors.toList());

        }
        throw new Exception("Traženi korisnik nema dozvolu!");
    }

    @Override
    public List<Training> getTrainings(UserDataFetching coach) throws Exception {
        if (AuthService.userCheck(coach.getUserID(),Role.COACH)) {
            return trainingRepo.findAll();

        }
        throw new Exception("Traženi korisnik nema dozvolu!");

    }

    @Override
    public List<Integer> getAvailableSchedule(UserScheduleDataFetching coach) throws Exception {
        if (AuthService.userCheck(coach.getUserID(),Role.COACH)) {
            LocalDate startDate = java.time.LocalDate.now();
            LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
            List<LocalDate> dates = startDate.datesUntil(endDate).collect(Collectors.toList());
            Set<Integer> timeInDay = new HashSet<>();
            Set<Integer> timePossible = new TreeSet<>();

            timePossible.add(8);

            timePossible.add(10);

            timePossible.add(12);

            timePossible.add(14);

            timePossible.add(16);

            timePossible.add(18);

            timePossible.add(20);

            for (LocalDate date : dates) {
                    if (date.getDayOfWeek().getValue() == coach.getDayOfWeek()) {

                        timeInDay.addAll(scheduleRepo.getAvailableSchedule(date));
                    }
            }
            timePossible.removeAll(timeInDay);
            return new LinkedList<>(timePossible);

        }
        throw new Exception("Traženi korisnik nema dozvolu!");
    }

    @Override
    public Message getHoursAvailable(UserHoursDataFetching userHoursDataFetching) throws Exception {

        if (AuthService.userCheck(userHoursDataFetching.getCoachID(), Role.COACH)) {
            try {
                Coach coach = em.getReference(Coach.class, userHoursDataFetching.getCoachID());
                if (coach.isVerified()!=true) {
                    throw new Exception("Još niste verificirani od strane admina!");
                }
                Client client = em.getReference(Client.class, userHoursDataFetching.getClientID());
                int hoursAvailable = userHoursDataFetching.getHoursAvailable();
                client.setHoursAvailable(hoursAvailable);
                client.setTotalHours(hoursAvailable);
                clientRepo.save(client);
                return new Message("OK");
            }
            catch (EntityNotFoundException e) {
                throw new Exception("Trening i/ili klijent i/ili trener s navedenim id-om ne postoji u bazi podataka!");
            }
        }
        throw new Exception("Traženi korisnik nema dozvolu za dodjelu fonda sati!");
    }

}
