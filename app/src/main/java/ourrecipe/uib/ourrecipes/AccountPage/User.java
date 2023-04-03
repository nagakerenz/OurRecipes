package ourrecipe.uib.ourrecipes.AccountPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User {
    public String userId, name, email, birthDate, selectedDate;


    public User(String userId, String name, String email, Calendar birthDate, String selectedDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.birthDate = format.format(birthDate.getTime());
        this.selectedDate = selectedDate != null ? selectedDate : "";

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

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setBirthDateAsDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dateString = format.format(date);
            this.birthDate = dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setBirthDate(Calendar birthDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.birthDate = format.format(birthDate.getTime());
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Date getBirthDateAsDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User() {

    }
}
