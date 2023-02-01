package progi.rest;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import progi.domain.User;
import progi.rest.dto.*;
import progi.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("https://group-fitness-planer-q3fc.onrender.com")
public class UserController {

    private UserService userService;


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

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/updateinfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateInfo(@RequestBody UserDataChangeFetching user) throws Exception{
            return ResponseEntity.ok().body(userService.updateInfo(user));

    }

    @PostMapping(value = "/deleteuser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@RequestBody UserDataFetching user) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(user));
    }
}
