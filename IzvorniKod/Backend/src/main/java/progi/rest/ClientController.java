package progi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.rest.dto.*;
import progi.service.ClientService;
import progi.service.ScheduleService;
import progi.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/client")
@CrossOrigin("https://group-fitness-planer-q3fc.onrender.com")
public class ClientController {

    private ClientService clientService;

    private UserService userService;

    private ScheduleService scheduleService;

    @Autowired
    ClientController(ClientService clientService, UserService userService, ScheduleService scheduleService) {
        this.clientService = clientService;
        this.userService = userService;
        this.scheduleService = scheduleService;
    }

    @PostMapping(value = "/trainings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTrainings(@RequestBody UserDataFetching client) {
        try {
            return ResponseEntity.ok().body(clientService.getTrainings(client));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));
        }

    }

    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSchedule(@RequestBody UserTrainingDataFetching userTraining) {
        try {
            return ResponseEntity.ok().body(clientService.getSchedule(userTraining));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));

        }
    }

    @PostMapping(value = "/reservations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getReservations(@RequestBody UserDataFetching client) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.getReservations(client));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));
        }

    }



    @PostMapping(value = "/makereservation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity makeReservation(@RequestBody ClientReservation client) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.makeReservation(client));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }


    }
    @PostMapping(value = "/cancelreservation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity cancelReservation(@RequestBody ClientReservation client) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.cancelReservation(client));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }


    }

    @PostMapping(value = "/changegoal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changeGoal(@RequestBody ClientGoal clientGoal) {
        try {
            if(clientService.checkWeek()){
                return ResponseEntity.status(HttpStatus.OK).body(clientService.changeGoal(clientGoal));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Promjena cilja moguća je samo zadnji tjedan mjeseca!"));
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));
        }

    }

    @PostMapping(value = "/checkgoal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkGoals(@RequestBody UserDataFetching user) {
        try {
            clientService.resetClientsWithNextGoal();
            return ResponseEntity.status(HttpStatus.OK).body(new Message("OK"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));
        }
    }

    @PostMapping(value = "/newSchedules", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity newSchedules(@RequestBody UserDataFetching user) {
        try {
            scheduleService.updateTrainingSchedule();
            return ResponseEntity.status(HttpStatus.OK).body(new Message("OK"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));
        }
    }
}

