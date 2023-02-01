//package progi;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import progi.rest.dto.ClientRegistration;
//import progi.rest.dto.CoachRegistration;
//import progi.service.UserService;
//
//
//@Component
//public class DataLoader implements ApplicationRunner {
//
//    private UserService userService;
//    @Autowired
//    DataLoader(UserService userService) {
//        this.userService = userService;
//    }
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//
//
//        ClientRegistration client1 = new ClientRegistration("John", "Smith", "01.01.1990", "1234567890", "johnsmith@gmail.com", "johnsmith", "password123", "povećanje mišićne mase");
//        ClientRegistration client2 = new ClientRegistration("Jane", "Doe", "14.02.1995", "0987654321", "janedoe@gmail.com", "janedoe", "password456", "povećanje mišićne mase");
//        ClientRegistration client3 = new ClientRegistration("James", "Johnson", "21.03.2000", "1112223333", "jamesjohnson@gmail.com", "jamesjohnson", "password789", "izdržljivost");
//        ClientRegistration client4 = new ClientRegistration("Emily", "Williams", "28.04.2005", "4445556666", "emilywilliams@gmail.com", "emilywilliams", "password101112", "izdržljivost");
//        ClientRegistration client5 = new ClientRegistration("Robert", "Jones", "05.05.2010", "7778889999", "robertjones@gmail.com", "robertjones", "password131415", "gubitak težine");
//
//        userService.createClient(client1, null);
//        userService.createClient(client2, null);
//        userService.createClient(client3, null);
//        userService.createClient(client4, null);
//        userService.createClient(client5, null);
//
//        CoachRegistration coach1 = new CoachRegistration("Sarah", "Smith", "01.01.1980", "1234567890", "sarahsmith@gmail.com", "sarahsmith", "coachpassword123");
//        CoachRegistration coach2 = new CoachRegistration("Michael", "Doe", "14.02.1985", "0987654321", "michaeldoe@gmail.com", "michaeldoe", "coachpassword456");
//        CoachRegistration coach3 = new CoachRegistration("Emily", "Johnson", "21.03.1990", "1112223333", "emilyjohnson@gmail.com", "emilyjohnson", "coachpassword789");
//        CoachRegistration coach4 = new CoachRegistration("David", "Williams", "28.04.1995", "4445556666", "davidwilliams@gmail.com", "davidwilliams", "coachpassword101112");
//        CoachRegistration coach5 = new CoachRegistration("Samantha", "Jones", "05.05.2000", "7778889999", "samanthajones@gmail.com", "samanthajones", "coachpassword131415");
//
//        userService.createCoach(coach1, null);
//        userService.createCoach(coach2, null);
//        userService.createCoach(coach3, null);
//        userService.createCoach(coach4, null);
//        userService.createCoach(coach5, null);
//
//
//        for (long i=1; i<=11; i++) {
//            userService.confirmEmail(i);
//        }
//    }
//}
