package progi.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import progi.domain.Coach;
import progi.domain.Role;
import progi.domain.Training;
import progi.domain.User;
import progi.repository.*;
import progi.rest.dto.AdminVerificationDeletion;
import progi.rest.dto.LoggedUser;
import progi.rest.dto.UserDataFetching;
import progi.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceJPA implements AdminService {


    private UserRepository userRepo;

    private CoachRepository coachRepo;

    private ClientRepository clientRepo;

    private ScheduleRepository scheduleRepo;

    private TrainingRepository trainingRepo;

    private JavaMailSender mailSender;

    AdminServiceJPA(UserRepository userRepo, CoachRepository coachRepo, ClientRepository clientRepo,ScheduleRepository scheduleRepo, TrainingRepository trainingRepo, JavaMailSender mailSender) {
        this.userRepo = userRepo;
        this.coachRepo = coachRepo;
        this.scheduleRepo = scheduleRepo;
        this.trainingRepo = trainingRepo;
        this.mailSender = mailSender;
        this.clientRepo = clientRepo;
    }
    @Override
    public List<LoggedUser> getUsers(UserDataFetching admin) throws Exception {
        if (AuthService.userCheck(admin.getUserID(), Role.ADMIN)) {
            List<LoggedUser> users = userRepo.findAll().stream().map(LoggedUser::new).collect(Collectors.toList());
            users.remove(0); //brisanje admina iz liste
            return users;
        }
        throw new Exception("Traženi korisnik nema dozvolu!");
    }

    @Override
    public boolean deleteUser(AdminVerificationDeletion admin) throws Exception {
        if (AuthService.userCheck(admin.getAdminID(), Role.ADMIN)) {
            try {
                User user = userRepo.findById(admin.getUserID()).get();
                List<User> users = null;
                if (user.getRole() == Role.COACH) {
                    System.out.println("coach");
                    Coach coach = (Coach) user;
                    List<Long> clientIDS = clientRepo.findClientsByTrainingIDs(coach.getTrainings().stream().map(Training::getTrainingID).collect(Collectors.toList()));
                    System.out.println("clientids: " +clientIDS);
                    clientRepo.deleteFromClientTrainingsByTrainingIDs(coach.getTrainings().stream().map(Training::getTrainingID).collect(Collectors.toList()));
                    clientRepo.deleteFromClientSchedulesByTrainingIDs(coach.getTrainings().stream().map(Training::getTrainingID).collect(Collectors.toList()));
                    scheduleRepo.deleteByTrainingIDs(coach.getTrainings().stream().map(Training::getTrainingID).collect(Collectors.toList()));
                    users = userRepo.findAllById(clientIDS);
                    System.out.println("after removing: " + users);
                    userRepo.deleteById(admin.getUserID());
                    System.out.println(users);
                    sendMails(users.stream().map(User::getEmail).collect(Collectors.toList()), "Obavijest o otkazivanju treninga", "Poštovani, obavještavamo Vas da je Vaš trener otkazao treninge. Molimo Vas da se obratite treneru za više informacija.");

                }
                else if(user.getRole() == Role.CLIENT) {
                    clientRepo.deleteReservations(user.getUserID());
                    coachRepo.deleteClientFromCoachClientsByClientID(user.getUserID());
                    userRepo.deleteById(admin.getUserID());
                }

                //List<User> users = userRepo.findAllById(clientIDS);
                System.out.println("deleted " + admin.getUserID());
                return true;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        throw new Exception("Traženi korisnik nema dozvolu!");

    }

    private void sendMails(List<String> mails, String subject, String message) {

        SimpleMailMessage email = new SimpleMailMessage();
        for (String mail: mails) {
            email.setTo(mail);
            email.setFrom("groupfitnessplanner@devups.com");
            email.setSubject(subject);
            email.setText(message);
            mailSender.send(email);
            System.out.println("POSLANO");
        }

    }

    @Override
    public boolean verifyCoach(AdminVerificationDeletion admin) throws Exception {
        if (AuthService.userCheck(admin.getAdminID(), Role.ADMIN)) {
            try {
                Coach coach = coachRepo.findById(admin.getUserID()).get();
                coach.setVerified(true);
                coachRepo.save(coach);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        throw new Exception("Traženi korisnik nema dozvolu!");
    }
}

