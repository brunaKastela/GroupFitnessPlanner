package progi.service;

import org.springframework.stereotype.Service;
import progi.domain.Client;
import progi.domain.Coach;
import progi.domain.User;
import progi.rest.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public interface UserService {
    Client createClient(ClientRegistration client, HttpServletRequest request) throws Exception;

    Coach createCoach(CoachRegistration coach, HttpServletRequest request) throws Exception;

    LoggedUser login(String username, String password) throws Exception;

    boolean deleteUser(UserDataFetching admin) throws Exception;

    boolean isVerified(User user);

    void confirmEmail(Long userID);

    Optional<User> findById(long userID);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean usernameAvailable(String username);

    boolean emailAvailable(String email);

    LoggedUser updateInfo(UserDataChangeFetching user) throws Exception;

    public boolean usernameAvailability(UserDataChangeFetching user) throws Exception;
}
