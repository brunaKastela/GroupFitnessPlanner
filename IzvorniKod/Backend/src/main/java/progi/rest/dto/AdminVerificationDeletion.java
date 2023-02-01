package progi.rest.dto;

public class AdminVerificationDeletion {
    private String userID;
    private String adminID;

    public AdminVerificationDeletion(String userID, String adminID) {
        this.userID = userID;
        this.adminID = adminID;
    }

    public long getUserID() {
        return Long.parseLong(userID);
    }

    public long getAdminID() {
        return Long.parseLong(adminID);
    }
}

