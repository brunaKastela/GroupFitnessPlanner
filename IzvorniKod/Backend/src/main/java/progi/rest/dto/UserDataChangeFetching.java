package progi.rest.dto;

import progi.domain.Role;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserDataChangeFetching {

    private Long userID;

    private String name;


    private String surname;


    private LocalDate dateOfBirth;

    @Size(min = 5, max = 20, message = "Korisničko ime mora imati između 3 i 20 znakova!")
    private String username;
    @Size(min = 8, max = 20, message = "Lozinka mora imati između 8 i 20 znakova!")
    private String password;

    private String contact;

    private String newPassword;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public UserDataChangeFetching(Long userID, String name, String surname, String dateOfBirth, String username, String newPassword, String password, String contact) throws Exception {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        try {
            LocalDate date = LocalDate.parse(dateOfBirth, formatter);
            this.dateOfBirth = date;
        }
        catch (Exception e) {
            throw new Exception("Datum rođenja mora biti u formatu dd.MM.yyyy");
        }
        this.username = username;
        this.password = password;
        if(newPassword != "") { this.newPassword = newPassword; }
        this.contact = contact;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
