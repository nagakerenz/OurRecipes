package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import ourrecipe.uib.ourrecipes.BottomNavigationBar;
import ourrecipe.uib.ourrecipes.PreferencePage;
import ourrecipe.uib.ourrecipes.R;

public class LoginPage extends AppCompatActivity {

    EditText logInEmail, logInPassword;
    Button loginID;
    FirebaseAuth mAuth;
    Button signup;
    Button forget;
    GoogleSignInOptions options;
    GoogleSignInClient client;
    ImageButton logInGoogle;
    private boolean isBackPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_login_page);

        mAuth = FirebaseAuth.getInstance();
        loginID = findViewById(R.id.login);
        logInEmail = findViewById(R.id.username);
        logInPassword = findViewById(R.id.password);
        logInGoogle = findViewById(R.id.googleIcon);
        signup = (Button) findViewById(R.id.signup);
        forget = (Button) findViewById(R.id.forget_password);


        //THIS IS FOR HANDLING CASUAL LOG IN
        loginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(logInEmail.getText().toString());
                password = String.valueOf(logInPassword.getText().toString());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            // After successful login, check if this is the user's first time logging in
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                            boolean isFirstTimeLogin = preferences.getBoolean("isFirstTimeLogin_" + currentUser.getUid(), true);
                            if (isFirstTimeLogin) {
                                // User is logging in for the first time, go to sign up page
                                startActivity(new Intent(LoginPage.this, PreferencePage.class));
                                preferences.edit().putBoolean("isFirstTimeLogin_" + currentUser.getUid(), false).apply();
                            } else {
                                // User is not logging in for the first time, go to main activity
                                startActivity(new Intent(LoginPage.this, BottomNavigationBar.class));
                            }
                            finish(); // prevent the user from returning to the login activity via the back button
                        } else {
                            Toast.makeText(LoginPage.this, "Login failed. " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //THIS IS FOR HANDLING GOOGLE LOG IN
        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(LoginPage.this, options);
        logInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i, 1234);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgetPassword();
            }
        });

        getSupportActionBar().hide();
    }

    //THIS IS FOR HANDLING FACEBOOK SIGN IN


    //THIS IS FOR HANDLING GOOGLE SIGN IN
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                boolean isFirstTimeLogin = preferences.getBoolean("isFirstTimeLogin_" + currentUser.getUid(), true);
                                if (isFirstTimeLogin) {
                                    // User is logging in for the first time, go to sign up page
                                    startActivity(new Intent(LoginPage.this, PreferencePage.class));
                                    preferences.edit().putBoolean("isFirstTimeLogin_" + currentUser.getUid(), false).apply();
                                } else {
                                    // User is not logging in for the first time, go to main activity
                                    startActivity(new Intent(LoginPage.this, BottomNavigationBar.class));
                                }
                                finish(); // prevent the user from returning to the login activity via the back button
                            } else {
                                Toast.makeText(LoginPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void openSignUp() {
        Intent signup = new Intent(LoginPage.this, SignUpPage.class);
        startActivity(signup);
    }
    public void openForgetPassword() {
        Intent forget = new Intent(LoginPage.this, ForgotPasswordPage.class);
        startActivity(forget);
    }

    @Override
    public void onBackPressed() {
        if (isBackPressedOnce){
            finishAffinity();
            finish();
            return;
        }

        Toast.makeText(LoginPage.this, "Press Again To Exit Apps!", Toast.LENGTH_SHORT).show();
        isBackPressedOnce = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        },2000);


    }
}