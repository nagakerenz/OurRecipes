package ourrecipe.uib.ourrecipes.AccountPage;

public class User {
    public String userId, name, age, email;

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public User(String userId, String name, String age, String email) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public User() {

    }
}
