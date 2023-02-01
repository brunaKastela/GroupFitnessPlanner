package progi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import progi.domain.VerificationToken;

import javax.transaction.Transactional;

public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {
    @Query(value = "SELECT * FROM TOKENS token WHERE token = ?", nativeQuery = true)
    String getVerificationToken(String token);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO TOKENS(USERID,TOKEN) VALUES(?,?)", nativeQuery = true)
    int save(Long userID, String verificationToken);
}
