package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ourrecipe.uib.ourrecipes.BottomNavigationBar;
import ourrecipe.uib.ourrecipes.PreferencePage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.ui.profile.ProfileFragment;

public class SignUpPage extends AppCompatActivity {

    EditText signUpName, signUpAge, signUpEmail, signUpPassword, signUpConfirmPassword;
    Button signUp;
    Button login;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_signup_page);

        mAuth = FirebaseAuth.getInstance();
        signUpName = findViewById(R.id.name);
        signUpAge = findViewById(R.id.age);
        signUpEmail = findViewById(R.id.email);
        signUpPassword = findViewById(R.id.password);
        signUpConfirmPassword = findViewById(R.id.confirmPassword);
        signUp = findViewById(R.id.signUp);
        progressBar = findViewById(R.id.loading);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, age, email, password, confirmPassword;
                name = String.valueOf(signUpName.getText().toString());
                age = String.valueOf(signUpAge.getText().toString());
                email = String.valueOf(signUpEmail.getText().toString());
                password = String.valueOf(signUpPassword.getText().toString());
                confirmPassword = String.valueOf(signUpConfirmPassword.getText().toString());

                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(SignUpPage.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    signUpName.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    signUpEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    signUpEmail.setError("Please Provide A Valid Email");
                    signUpEmail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    signUpPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(SignUpPage.this, "Your password must have at least 6 characters", Toast.LENGTH_SHORT).show();
                    signUpPassword.requestFocus();
                    signUpConfirmPassword.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpPage.this, "Both Password Must Be The Same", Toast.LENGTH_SHORT).show();
                    signUpConfirmPassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(view.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

//                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
//                            usersRef.child(userId).child("name").setValue(name);

//                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
//                            usersRef.child(userId).child("name").setValue(name);

                            User user = new User(name, age, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(SignUpPage.this, "SignUp Successful.",
                                                        Toast.LENGTH_SHORT).show();
                                                // After successful sign up, go back to login page
                                                startActivity(new Intent(SignUpPage.this, LoginPage.class));
                                                finish(); // prevent the user from returning to the sign up activity via the back button
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(SignUpPage.this, "SignUp Failed. " + task.getException().getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(SignUpPage.this, "SignUp Failed. " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
        getSupportActionBar().hide();
    }

    public void openLogin() {
        Intent login = new Intent(SignUpPage.this, LoginPage.class);
        startActivity(login);
    }
}