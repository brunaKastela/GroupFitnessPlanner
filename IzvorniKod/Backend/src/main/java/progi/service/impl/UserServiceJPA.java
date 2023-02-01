package progi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.domain.*;
import progi.repository.*;
import progi.rest.dto.*;
import progi.service.GoalService;
import progi.service.UserDataChangeException;
import progi.service.UserService;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceJPA implements UserService {
    private String URLLocal = "http://localhost:8080";
    private final String URL = "https://group-fitness-planer-q3fc.onrender.com";
    private final UserRepository userRepo;

    private CoachRepository coachRepo;

    private ClientRepository clientRepo;

    private ScheduleRepository scheduleRepo;

    private TrainingRepository trainingRepo;

    private JavaMailSender mailSender;
    private final ApplicationEventPublisher eventPublisher;
    private final GoalService goalService;
    private final PasswordEncoder passwordEncoder;


    private EntityManager em;
    @Autowired
    public UserServiceJPA(UserRepository userRepo, CoachRepository coachRepo, ClientRepository clientRepo, ScheduleRepository scheduleRepo, TrainingRepository trainingRepo,
                          ApplicationEventPublisher eventPublisher, GoalService goalService, EntityManager em, JavaMailSender mailSender) {
        this.userRepo = userRepo;
        this.coachRepo = coachRepo;
        this.clientRepo = clientRepo;
        this.scheduleRepo = scheduleRepo;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.goalService = goalService;
        this.em = em;
        this.mailSender = mailSender;
    }


    private boolean checkAvailability(UserRegistration user) throws Exception{
        if (!usernameAvailable(user.getUsername())) {
            throw new Exception("Korisnik sa danim korisničkim imenom već postoji!");
        }
        if (!emailAvailable(user.getEmail())) {
            throw new Exception("Korisnik sa danom mail adresom već postoji!");
        }
        if (user.getPassword().length()<8 || user.getPassword().length()>20)
            throw new Exception("Loznika mora biti duljine između 8 i 20 znakova!");

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return true;
    }

    @Override
    public boolean usernameAvailability(UserDataChangeFetching user) throws Exception {
        System.out.println(user.getUserID()+"  " +user.getUsername());
        int result = userRepo.findByUsernameAndUserIdNot(user.getUsername(), user.getUserID());
        System.out.println(result);
        if(result==0) {
            return true;
        }
        throw new Exception("Korisnik sa danim korisničkim imenom već postoji!");
    }

    @Override
    public Client createClient(ClientRegistration client, HttpServletRequest request) throws Exception {



            checkAvailability(client);
            Role role = Role.CLIENT;
            Goal goal = goalService.getGoalByGoalName(client.getGoal());
            if (goal==null) {
                throw new Exception("Unesite validan cilj!");
            }
            String token = UUID.randomUUID().toString();
            Client clientDB = new Client(client.getName(), client.getSurname(), client.getDateOfBirth(), client.getEmail(), client.getUsername(), client.getPassword(), client.getContact(), goal, role, token);
            Client saved = userRepo.save(clientDB);
            if (request!=null)
                sendMail(client,token, request);
            return saved;


    }

    @Override
    public Coach createCoach(CoachRegistration coach, HttpServletRequest request) throws Exception {

            checkAvailability(coach);
            Role role = Role.COACH;
            String token = UUID.randomUUID().toString();
            Coach coachDB = new Coach(coach.getName(),coach.getSurname(),coach.getDateOfBirth(),coach.getEmail(),coach.getUsername(),coach.getPassword(), coach.getContact(),role, token);
            Coach saved = userRepo.save(coachDB);
//            if (request!=null) {
//                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(coachDB, request.getContextPath()));
//            }
            if (request!=null)
                sendMail(coach, token, request);


            return saved;


    }

    private void sendMail(UserRegistration user, String token, HttpServletRequest request) {
        String userEmail = user.getEmail();
        String subject = "Registration confirmation";
        String confirmationUrl = request.getContextPath() + "/registration/registrationconfirmation?token=" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userEmail);
        email.setFrom("groupfitnessplanner@devups.com");
        email.setSubject(subject);
        email.setText("SUCCESSFUL REGISTRATION!" + "\r\n" + "https://group-fitness-planer-wf93.onrender.com" + confirmationUrl);
        mailSender.send(email);
    }


    @Override
    public LoggedUser login(String username, String password) throws Exception {
        Optional<User> user = userRepo.findByUsername(username);
        System.out.println(user);
        if (user.isPresent()) {
            if (isVerified(user.get()) ) {
                System.out.println(passwordEncoder.encode(user.get().getPassword()));
                if (passwordEncoder.matches(password,user.get().getPassword())) {
                    return new LoggedUser(user.get());
                }

                throw new Exception("Pogrešno korisničko ime i/ili lozinka!");


            }
            throw new Exception("Korisnički račun nije verificiran!");


        }
        throw new Exception("Ne postoji korisnik sa navedenim korisničkim imenom!");

    }

    @Override
    public LoggedUser updateInfo(UserDataChangeFetching user) throws Exception{

        String dataCheck = checkData(user);
        if(dataCheck != null) {
            throw new UserDataChangeException(dataCheck);
        }

        usernameAvailability(user);
        Optional<User> userTest = userRepo.findById(user.getUserID());

        if(userTest.isPresent()) {

            if (passwordEncoder.matches(user.getPassword(),userTest.get().getPassword())) {
                User userDB = userTest.get();
                userDB.setName(user.getName());
                userDB.setSurname(user.getSurname());
                userDB.setDateOfBirth(user.getDateOfBirth());
                userDB.setUsername(user.getUsername());
                if(user.getNewPassword() != null) {
                    String newPassword = passwordEncoder.encode(user.getNewPassword());
                    user.setNewPassword(newPassword);
                    userDB.setPassword(user.getNewPassword());
                }
                userDB.setContact(user.getContact());
                userRepo.save(userDB);

                Optional<User> userResult = userRepo.findById(user.getUserID());

                if (userResult.isPresent()) {
                    return new LoggedUser(userResult.get());

                    //if (userResult.get().getRole().equals(Role.CLIENT)) {
                    //    Client client = em.find(Client.class,userResult.get().getUserID());
                    //    return new LoggedUser(userResult.get().getUserID(),userResult.get().getUsername(),userResult.get().getName(),userResult.get().getSurname(),userResult.get().getDateOfBirth(),userResult.get().getEmail(),userResult.get().getContact(),userResult.get().getRole(),client.getCurrentGoal());
                    //}
                    //else if (userResult.get().getRole().equals(Role.COACH)) {
                    //    return new LoggedUser(userResult.get().getUserID(),userResult.get().getUsername(),userResult.get().getName(),userResult.get().getSurname(),userResult.get().getDateOfBirth(),userResult.get().getEmail(),userResult.get().getContact(),userResult.get().getRole());
                    //}
                    //else {
                    //    return new LoggedUser(userResult.get().getUserID(),userResult.get().getUsername(),userResult.get().getName(),userResult.get().getSurname(),userResult.get().getDateOfBirth(),userResult.get().getEmail(),userResult.get().getContact(),userResult.get().getRole());
                    //}
                }
            } else {
                throw new Exception("Neispravna lozinka!");
            }
        } else {
            throw new Exception("Promjene nisu uspješno provedene!");
        }
        return null;
    }

    public String checkData(UserDataChangeFetching user) {
        if(user.getUsername().length() < 5 || user.getUsername().length() > 20) {
            return new String("Korisničko ime mora imati duljinu između 5 i 20 znakova!");
        }
        if(user.getContact().length() < 8 || user.getContact().length() > 10) {
            return new String("Kontakt mora imati duljinu između 8 i 10 znakova!");
        }
        if(user.getNewPassword() != null && (user.getNewPassword().length() < 8 || user.getNewPassword().length() > 20)) {
            return new String("Nova lozinka mora imati duljinu između 8 i 20 znakova!");
        }
        return null;
    }

    @Override
    public boolean deleteUser(UserDataFetching admin) throws Exception {

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
            //userRepo.deleteById(admin.getUserID());
            //List<User> users = userRepo.findAllById(clientIDS);
            //System.out.println(users);
            //sendMails(users.stream().map(User::getEmail).collect(Collectors.toList()), "Obavijest o otkazivanju treninga", "Poštovani, obavještavamo Vas da je Vaš trener otkazao treninge. Molimo Vas da se obratite treneru za više informacija.");
            System.out.println("deleted " + admin.getUserID());
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean isVerified(User user) {
        return user.getMailVerified();
    }

    @Override
    public void confirmEmail(Long userID) {
        User user = userRepo.findById(userID).get();
        user.setMailVerified(true);
        userRepo.save(user);
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
    public Optional<User> findById(long userID) {
        return userRepo.findById(userID);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public boolean usernameAvailable(String username) {
        return userRepo.countByUsername(username) == 0;
    }

    @Override
    public boolean emailAvailable(String email) {
        return userRepo.countByEmail(email) == 0;
    }

}
