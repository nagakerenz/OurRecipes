package ourrecipe.uib.ourrecipes.AccountPage;

public class UserDataClass {

    private String dataName;
    private String dataAge;
    private String dataEmail;
    private String dataImage;

    public UserDataClass(String dataName, String dataAge, String dataEmail, String dataImage) {
        this.dataName = dataName;
        this.dataAge = dataAge;
        this.dataEmail = dataEmail;
        this.dataImage = dataImage;
    }



    public String getDataName() {
        return dataName;
    }

    public String getDataAge() {
        return dataAge;
    }

    public String getDataEmail() {
        return dataEmail;
    }

    public String getDataImage() {
        return dataImage;
    }

}
