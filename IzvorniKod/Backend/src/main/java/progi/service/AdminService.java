package progi.service;

import progi.rest.dto.AdminVerificationDeletion;
import progi.rest.dto.LoggedUser;
import progi.rest.dto.UserDataFetching;

import java.util.List;


public interface AdminService {
    List<LoggedUser> getUsers(UserDataFetching admin) throws Exception;

    boolean deleteUser(AdminVerificationDeletion admin) throws Exception;

    boolean verifyCoach(AdminVerificationDeletion admin) throws Exception;
}
