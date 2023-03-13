package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ourrecipe.uib.ourrecipes.R;

public class AccountPage extends AppCompatActivity {
    Button email;
    Button password;
    Button logout;
    Button delete;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_account_page);

        mAuth = FirebaseAuth.getInstance();
        email = (Button) findViewById(R.id.changeEmail);
        password = (Button) findViewById(R.id.changePassword);
        logout = (Button) findViewById(R.id.logOut);
        delete = (Button) findViewById(R.id.deleteAccount);


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailPage();
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPasswordPage();
            }
        });

        user = mAuth.getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AccountPage.this, "Account Logout!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AccountPage.this, LoginPage.class));
                finish(); // prevent the user from returning to the main activity via the back button
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountPage.this, "Account Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openEmailPage() {
        Intent email = new Intent(this, ChangeEmail.class);
        startActivity(email);
    }

    public void openPasswordPage() {
        Intent password = new Intent(this, ChangePassword.class);
        startActivity(password);
    }
}