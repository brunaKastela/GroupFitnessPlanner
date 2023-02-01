package progi.rest.dto;

import progi.domain.Role;

public class CoachRegistration extends UserRegistration{
    public CoachRegistration(String name, String surname, String dateOfBirth, String contact, String email, String username, String password) throws Exception{
        super(name, surname, dateOfBirth, contact, email, username, password);
    }
}
