package progi.domain;

import javax.persistence.*;

@Entity
@Table(name="TOKENS")
public class VerificationToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userID")
    private User user;

    private String token;

    public VerificationToken() {

    }

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }





}
