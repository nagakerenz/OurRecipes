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
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_account_page);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        } else {

        }

        email = (Button) findViewById(R.id.changeEmail);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailPage();
            }
        });

        password = (Button) findViewById(R.id.changePassword);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPasswordPage();
            }
        });

        logout = (Button) findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AccountPage.this, "Account Logout!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        delete = (Button) findViewById(R.id.deleteAccount);
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