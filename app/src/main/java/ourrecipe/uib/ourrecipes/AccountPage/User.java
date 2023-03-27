package ourrecipe.uib.ourrecipes.AccountPage;

public class User {

    public String name, age, email;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public User(String name, String age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public User() {

    }

}
