package progi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.domain.Role;
import progi.domain.User;
import progi.service.UserService;

import java.util.Optional;
@Service
public class AuthService {


    private static UserService userService;

    @Autowired
    AuthService(UserService userService) {
        this.userService = userService;
    }

    public static boolean userCheck(Long userID, Role role) {
        Optional<User> userTest = userService.findById(userID);
        if (userTest.isPresent()) {
            User userDB = userTest.get();
            if (userDB.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
