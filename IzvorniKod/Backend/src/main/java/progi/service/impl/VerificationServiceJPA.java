package progi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.domain.User;
import progi.repository.UserRepository;
import progi.service.VerificationService;

import java.util.UUID;

@Service
public class VerificationServiceJPA implements VerificationService {


    private UserRepository userRepo;
    @Autowired
    VerificationServiceJPA( UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void confirmRegistration(String token) {
        try {
            userRepo.confirmEmail(token);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);

        }
    }
//    @Override
//    public void createVerificationToken(User user, String token) {
//        try {
//
//            VerificationToken verificationToken = new VerificationToken(user,token);
//
//            verificationRepository.save(verificationToken);
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
//
//    @Override
//    public String getVerificationToken(String token) {
//        return verificationRepository.getVerificationToken(token);
//    }

}
