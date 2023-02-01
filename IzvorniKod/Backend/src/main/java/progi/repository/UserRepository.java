package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import progi.domain.Client;
import progi.domain.Coach;
import progi.domain.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);



    @Query("SELECT count(u) FROM User u WHERE u.username = :username")
    int countByUsername(@Param("username") String username);

    @Query("SELECT count(u) FROM User u WHERE u.email = :email")
    int countByEmail(@Param("email") String email);
    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET mail_Verified = true WHERE verification_token=?1", nativeQuery = true)
    void confirmEmail(@Param("userID") String token);

    

    @Query(value = "SELECT count(USERS.userID) FROM USERS WHERE USERS.userID!=?2 and USERS.username=?1 ", nativeQuery = true)
    int findByUsernameAndUserIdNot(String username, Long userId);
}
