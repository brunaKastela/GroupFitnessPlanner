package progi.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.domain.User;
import progi.rest.dto.LoggedUser;
import progi.rest.dto.Message;
import progi.rest.dto.LoginForm;

import progi.service.UserService;

@RestController
@RequestMapping("/login")
@CrossOrigin("https://group-fitness-planer-q3fc.onrender.com")
public class LoginController {


    private final UserService userService;
    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user")
    public ResponseEntity loginUser(@RequestBody LoginForm loginForm) throws Exception {
        try {
            LoggedUser user = userService.login(loginForm.getUsername(), loginForm.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }




    }






}
