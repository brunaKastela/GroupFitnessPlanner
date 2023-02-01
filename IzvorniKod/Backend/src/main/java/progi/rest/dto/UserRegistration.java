package progi.rest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class UserRegistration {
    @NotEmpty(message = "Ime ne može biti prazno!")
    private String name;

    @NotEmpty(message = "Prezime ne može biti prazno!")
    private String surname;

    @NotNull(message = "Datum rođenja ne može biti prazan!")
    private LocalDate dateOfBirth;

    @Email(message = "Email mora biti u ispravnom formatu!")
    @NotEmpty(message = "Email ne smije biti prazan!")
    private String email;

    @Size(min = 5, max = 20, message = "Korisničko ime mora biti duljine 5-20 znakova")
    @NotEmpty(message = "Korisničko ime ne smije biti prazno!")
    private String username;

    @Size(min = 5, max = 20, message = "Lozinka mora biti duljine 8-20 znakova")
    private String password;

    @Size(min = 8, max = 10, message = "Kontakt broj mora biti duljine 8-10 znakova!")
    private String contact;

    private String role;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public UserRegistration(String name, String surname, String dateOfBirth, String contact, String email, String username, String password) throws Exception {
        this.name = name;
        this.surname = surname;
        try {
            LocalDate date = LocalDate.parse(dateOfBirth, formatter);
            this.dateOfBirth = date;
        }
        catch (Exception e) {
            System.out.println("here"+e.getMessage());
            throw new Exception("Datum rođenja mora biti u formatu dd.MM.yyyy");
        }

        this.email = email;
        this.username = username;
        this.password = password;
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public void setSurname(String lastName) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
