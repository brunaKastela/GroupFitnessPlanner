package progi.rest;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import progi.rest.dto.ClientRegistration;
import progi.rest.dto.CoachRegistration;
import progi.rest.dto.Message;
import progi.service.GoalService;
import progi.service.UserService;
import progi.service.VerificationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registration")
@CrossOrigin("https://group-fitness-planer-q3fc.onrender.com")
public class RegistrationController {
    private final VerificationService verificationService;
    private final UserService userService;
    private final GoalService goalService;
    private final String URL = "https://group-fitness-planer-q3fc.onrender.com";
    private final String URLLocal = "http://localhost:3000";

    @ExceptionHandler({Exception.class, JsonMappingException.class})
    public ResponseEntity exceptions(Exception ex){
        if (ex.getClass().equals(MethodArgumentNotValidException.class)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(((MethodArgumentNotValidException)ex).getAllErrors().get(0).getDefaultMessage()));

        }
        else {
            if (ex.getClass().equals(HttpMessageNotReadableException.class)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(((HttpMessageNotReadableException)ex).getMostSpecificCause().getMessage()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(ex.getMessage()));
        }
    }


    @Autowired
    RegistrationController(VerificationService verificationService, UserService userService, GoalService goalService) {
        this.verificationService = verificationService;
        this.userService = userService;
        this.goalService = goalService;
    }

    @GetMapping(value = "/goals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getGoals() {
        List<String> goals = goalService.getGoals().stream().map(e -> e.getGoalName()).collect(Collectors.toList());
        return ResponseEntity.ok().body(goals);
    }

    @GetMapping("/registrationconfirmation")
    public RedirectView confirmRegistration(@RequestParam("token") String token) throws Exception {
            verificationService.confirmRegistration(token);
            return new RedirectView(URL + "/login");
    }

    @PostMapping(value = "/client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> createClient(@RequestBody @Valid ClientRegistration clientReg, HttpServletRequest request) throws Exception {
            userService.createClient(clientReg, request);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("OK"));
    }

    @PostMapping(value = "/coach", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> createCoach(@RequestBody @Valid CoachRegistration coachReg, HttpServletRequest request) throws Exception{
            userService.createCoach(coachReg, request);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("OK"));
    }


}
