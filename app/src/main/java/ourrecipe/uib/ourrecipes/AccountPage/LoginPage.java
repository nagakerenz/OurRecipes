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

import ourrecipe.uib.ourrecipes.BottomNavigationBar;
import ourrecipe.uib.ourrecipes.PreferencePage;
import ourrecipe.uib.ourrecipes.R;

public class LoginPage extends AppCompatActivity {

    EditText logInEmail, logInPassword;
    Button loginID;
    FirebaseAuth mAuth;
    Button signup;
    Button forget;
    GoogleSignInClient client;
    ImageButton logInGoogle;
    private boolean isBackPressedOnce = false;


//  if i turn it on, dia kalau misalnya lagi sign up terus tekan signup dia langsung otomatis ke Home page,
//  sedangkan aku maunya kalau setelah sign up itu ke login page dlu

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), BottomNavigationBar.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_login_page);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(LoginPage.this, options);
        logInGoogle = findViewById(R.id.googleIcon);
        logInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i, 1234);
            }
        });

        logInEmail = findViewById(R.id.username);
        logInPassword = findViewById(R.id.password);
        loginID = findViewById(R.id.login);
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
                                    Intent intent = new Intent(getApplicationContext(), PreferencePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginPage.this, "Login failed. " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

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
//       ini buat kalau misalnya udah pernah sign up  ataupun baru login baru pertama kalinya pergi ke preference page
//        SharedPreferences prefs = getSharedPreferences("Preference", MODE_PRIVATE);
//        boolean isNewUser = prefs.getBoolean("isNewUser", false);
//
//        if (isNewUser) {
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("isNewUser", false);
//            editor.apply();
//            Intent intent = new Intent(LoginPage.this, PreferencePage.class);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(LoginPage.this, BottomNavigationBar.class);
//            startActivity(intent);
//        }


        getSupportActionBar().hide();
    }

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
                                    Intent intent = new Intent(getApplicationContext(), PreferencePage.class);
                                    startActivity(intent);
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
//Sama kek diatas
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user!= null) {
//            Intent intent = new Intent(this, PreferencePage.class);
//            startActivity(intent);
//        }
//    }

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