package progi.service;

import org.springframework.stereotype.Service;
import progi.domain.User;

@Service
public interface VerificationService {

    void confirmRegistration(String token);
}
