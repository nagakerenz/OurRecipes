package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AccountPage extends AppCompatActivity {
    Button email;
    Button password;
    Button logout;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);

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
                Toast.makeText(AccountPage.this, "Account Logout!", Toast.LENGTH_SHORT).show();
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