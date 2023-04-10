package ourrecipe.uib.ourrecipes.AccountPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User {
    public String userId, name, email, birthDate, profilePictureUrl;

    public User(String userId, String name, String email, String birthDate, String profilePictureUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getProfilePictureUrl() { // Add getter for profilePictureUrl
        return profilePictureUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setProfilePictureUrl(String profilePictureUrl) { // Add setter for profilePictureUrl
        this.profilePictureUrl = profilePictureUrl;
    }

    public User() {

    }
}
