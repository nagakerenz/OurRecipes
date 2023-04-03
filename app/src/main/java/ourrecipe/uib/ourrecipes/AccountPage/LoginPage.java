package ourrecipe.uib.ourrecipes.AccountPage;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import ourrecipe.uib.ourrecipes.BottomNavigationBar;
import ourrecipe.uib.ourrecipes.PreferencePage;
import ourrecipe.uib.ourrecipes.R;

public class LoginPage extends AppCompatActivity {

    EditText logInEmail, logInPassword;
    Button loginID;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Button signup;
    Button forget;
    CallbackManager mCallbackManager;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageButton logInGoogle, logInFacebook;
    private boolean isBackPressedOnce = false;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_login_page);

        mAuth = FirebaseAuth.getInstance();
        loginID = findViewById(R.id.login);
        logInEmail = findViewById(R.id.username);
        logInPassword = findViewById(R.id.password);
        logInGoogle = findViewById(R.id.googleIcon);
        logInFacebook = findViewById(R.id.facebookIcon);
        signup = (Button) findViewById(R.id.signup);
        forget = (Button) findViewById(R.id.forget_password);
        database = FirebaseDatabase.getInstance("https://ourrecipes-c601d-default-rtdb.asia-southeast1.firebasedatabase.app/");


        //THIS IS FOR HANDLING CASUAL LOG IN
        loginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(logInEmail.getText().toString().trim());
                password = String.valueOf(logInPassword.getText().toString().trim());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    logInEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    logInEmail.setError("Please Provide A Valid Email");
                    logInEmail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
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
                            dialog.dismiss();
                            Toast.makeText(LoginPage.this, "Login failed. " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //THIS IS FOR HANDLING PASSWORD VISIBILITY
        logInPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (logInPassword.getRight() - logInPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (logInPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        logInPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        logInPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        logInPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        logInPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, R.drawable.ic_baseline_visibility_off_24, 0);

                    }
                    logInPassword.setSelection(logInPassword.getText().length());
                    return true;
                }
            }
            return false;
        });

        //THIS IS FOR HANDLING FACEBOOK LOG IN
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        logInFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginPage.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });
            }
        });

        //THIS IS FOR HANDLING GOOGLE LOG IN
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(LoginPage.this, gso);
        logInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = gsc.getSignInIntent();
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


    //THIS IS FOR HANDLING GOOGLE AND FACEBOOK SIGN IN
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        //THIS IS FOR HANDLING FACEBOOK SIGN IN
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();

        //THIS IS FOR HANDLING GOOGLE SIGN IN
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
                                dialog.dismiss();

                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                user = mAuth.getCurrentUser();
                                assert user != null;

                                // create a new User object to store the Google user's name and email
                                User googleUser = new User();
                                googleUser.setUserId(user.getUid());
                                googleUser.setName(user.getDisplayName());
                                googleUser.setEmail(user.getEmail());

                                // get the birthdate from the user's Google account
                                String birthdate = String.valueOf(user.getMetadata().getCreationTimestamp());
                                // create a Calendar object for the birthdate
                                Calendar birthdateCalendar = Calendar.getInstance();
                                birthdateCalendar.setTimeInMillis(Long.parseLong(birthdate));

                                // set the birthdate on the user object
                                googleUser.setBirthDate(birthdateCalendar);

                                // set the selected date on the user object
                                String selectedDate = "dd/MM/yyyy"; // Replace with your desired date format and value
                                googleUser.setSelectedDate(selectedDate);

                                // save the Google user's information under the "GoogleUser" node in the Realtime Database
                                database.getReference().child("User Profile").child("GoogleUser").child(user.getUid()).setValue(googleUser);

                                // After successful login, check if this is the user's first time logging in
                                SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                boolean isFirstTimeLogin = preferences.getBoolean("isFirstTimeLogin_" + user.getUid(), true);
                                if (isFirstTimeLogin) {
                                    // User is logging in for the first time, go to sign up page
                                    Intent intent = new Intent(LoginPage.this, PreferencePage.class);
                                    intent.putExtra("name", Objects.requireNonNull(user.getDisplayName()).toString());
                                    startActivity(intent);
                                    preferences.edit().putBoolean("isFirstTimeLogin_" + user.getUid(), false).apply();
                                } else {
                                    // User is not logging in for the first time, go to main activity
                                    startActivity(new Intent(LoginPage.this, BottomNavigationBar.class));
                                }
                                finish(); // prevent the user from returning to the login activity via the back button
                            } else {
                                dialog.dismiss();
                                Toast.makeText(LoginPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    //THIS IS FOR HANDLING FACEBOOK LOGIN ACCESS
    private void handleFacebookAccessToken(AccessToken token) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            assert user != null;

                            // create a new User object to store the Facebook user's name and email
                            User facebookUser = new User();
                            facebookUser.setName(user.getDisplayName());
                            facebookUser.setEmail(user.getEmail());

                            // save the Facebook user's information under the "FacebookUser" node in the Realtime Database
                            database.getReference().child("User Profile").child("FacebookUser").child(user.getUid()).setValue(facebookUser);

                            // After successful login, check if this is the user's first time logging in
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                            boolean isFirstTimeLogin = preferences.getBoolean("isFirstTimeLogin_" + currentUser.getUid(), true);
                            if (isFirstTimeLogin) {
                                // User is logging in for the first time, go to sign up page
                                Intent intent = new Intent(LoginPage.this, PreferencePage.class);
                                intent.putExtra("name", Objects.requireNonNull(user.getDisplayName()).toString());
                                startActivity(intent);
                                preferences.edit().putBoolean("isFirstTimeLogin_" + currentUser.getUid(), false).apply();
                            } else {
                                // User is not logging in for the first time, go to main activity
                                startActivity(new Intent(LoginPage.this, BottomNavigationBar.class));
                            }
                            finish(); // prevent the user from returning to the login activity via the back button
                        } else {
                            dialog.dismiss();

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed." + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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