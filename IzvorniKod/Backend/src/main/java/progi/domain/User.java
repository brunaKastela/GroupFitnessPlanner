package progi.domain;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name="USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @NotEmpty(message = "Surname cannot be empty!")
    private String surname;


    @NotNull(message = "Date of birth cannot be empty!")
    private LocalDate dateOfBirth;


    @Email(message = "Email should be in valid format!")
    @NotEmpty(message = "Email should not be empty!")
    private String email;

    @Size(min = 5, max = 20)
    @NotEmpty(message = "Username cannot be empty!")
    private String username;

    private String password;

    @Size(min = 8, max = 10, message = "Contact field must be between 8 and 10 numbers!")
    private String contact;


    private Role role;

    public String getVerificationToken() {
        return verificationToken;
    }

    private String verificationToken;

    public User() {

    }

    public boolean getMailVerified() {
        return this.mailVerified;
    }

    public void setMailVerified(boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

    private boolean mailVerified;

    public boolean isMailVerified() {
        return mailVerified;
    }

    public Long getUserID() {
        return userID;
    }


    public User(String name, String surname, LocalDate dateOfBirth, String email, String username, String password, String contact, Role role, String verificationToken) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.username = username;
        this.password = password;
        this.contact = contact;
        this.role = role;
        this.verificationToken = verificationToken;
    }

    public User(String name, String surname, String username, String email, String contact) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.contact = contact;
    }

    public User(Long userID, String name, String surname, LocalDate dateOfBirth, String username, String password, String contact) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
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

    public void setSurname(String surname) {
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", name='" + name + '\'' + ", surname='" + surname + '\'' + ", dateOfBirth=" + dateOfBirth + ", email='" + email + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + ", contact='" + contact + '\'' + ", authLevel='" + role + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID.equals(user.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}
