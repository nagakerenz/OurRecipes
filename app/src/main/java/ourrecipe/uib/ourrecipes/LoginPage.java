package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPage extends AppCompatActivity {
    Button login;
    Button signup;
    Button forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreference();
            }
        });

        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });

        forget = (Button) findViewById(R.id.forget_password);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgetPassword();
            }
        });
        getSupportActionBar().hide();
    }

    public void openPreference() {
        Intent preference = new Intent(LoginPage.this, PreferencePage.class);
        startActivity(preference);
    }
    public void openSignUp() {
        Intent signup = new Intent(LoginPage.this, SignUpPage.class);
        startActivity(signup);
    }
    public void openForgetPassword() {
        Intent forget = new Intent(LoginPage.this, ForgotPasswordPage.class);
        startActivity(forget);
    }
}