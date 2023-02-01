package progi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progi.domain.User;
import progi.rest.dto.AdminVerificationDeletion;
import progi.rest.dto.Message;
import progi.rest.dto.UserDataFetching;
import progi.service.AdminService;
import progi.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("https://group-fitness-planer-q3fc.onrender.com")
public class AdminController {

    private UserService userService;
    private AdminService adminService;

    @Autowired
    public AdminController(UserService userService, AdminService adminService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(@RequestBody UserDataFetching admin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.getUsers(admin));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));
        }
    }

    @PostMapping(value = "/verifycoach", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity verifyCoach(@RequestBody AdminVerificationDeletion admin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.verifyCoach(admin));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }
    }

    @PostMapping(value = "/deleteuser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@RequestBody AdminVerificationDeletion admin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteUser(admin));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        }
    }

}
