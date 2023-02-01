package progi.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import progi.rest.dto.*;
import progi.service.CoachService;
import progi.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/coach")
@CrossOrigin("https://group-fitness-planer-q3fc.onrender.com")
public class CoachController {

    private CoachService coachService;
    private UserService userService;


    @Autowired
    CoachController(CoachService coachService, UserService userService) {
        this.coachService = coachService;
        this.userService = userService;
    }

    @ExceptionHandler({Exception.class})
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


    @PostMapping(value = "/workouts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getWorkouts(@RequestBody UserDataFetching coach) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(coachService.getWorkouts(coach));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));

        }
    }


    @PostMapping(value = "/potentialclients", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPotentialClients(@RequestBody UserDataFetching coach) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(coachService.getPotentialClients(coach));

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));

        }

    }

    @PostMapping(value = "/createtraining", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createTraining(@RequestBody @Valid ScheduleTraining scheduleTraining) {
        try {


            return ResponseEntity.status(HttpStatus.OK).body(coachService.createTraining(scheduleTraining));

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }
    }

    @PostMapping(value = "/trainings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTrainings(@RequestBody UserDataFetching coach) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(coachService.getTrainings(coach));

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));

        }

    }


    @PostMapping(value = "/assigntraining", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity assignTraining(@RequestBody AssignTraining assignTraining) {
        try {


            return ResponseEntity.status(HttpStatus.OK).body(coachService.assignTraining(assignTraining));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }
    }

    @PostMapping(value = "/getavailableschedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAvailableSchedule(@RequestBody UserScheduleDataFetching userScheduleDataFetching) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(coachService.getAvailableSchedule(userScheduleDataFetching));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }
    }

    @PostMapping(value = "/gethoursavailable", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAvailableSchedule(@RequestBody UserHoursDataFetching userHoursDataFetching) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(coachService.getHoursAvailable(userHoursDataFetching));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }
    }

}
