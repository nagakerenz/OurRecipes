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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                // Get user information
                                String userId, name, email;
                                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                name = account.getDisplayName();
                                email = account.getEmail();

                                // Retrieve the user's profile picture URL
                                String photoUrl = null;
                                if (account.getPhotoUrl() != null) {
                                    photoUrl = account.getPhotoUrl().toString();
                                }

                                // Upload the profile picture to Firebase Storage
                                if (photoUrl != null) {
                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                    StorageReference profilePicRef = storageRef.child("User Profile Pictures").child(userId + ".jpg");

                                    // Create a byte array stream from the URL of the profile picture
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    Glide.with(LoginPage.this)
                                            .asBitmap()
                                            .load(photoUrl)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    resource.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                                    byte[] data = baos.toByteArray();

                                                    // Upload the byte array to Firebase Storage
                                                    UploadTask uploadTask = profilePicRef.putBytes(data);
                                                    uploadTask.addOnCompleteListener(task -> {
                                                        if (task.isSuccessful()) {
                                                            // Get the download URL of the uploaded profile picture
                                                            profilePicRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                                                String profilePicUrl = uri.toString();

                                                                User googleUser = new User(userId, name, email, null, profilePicUrl); // Update User object with profile picture URL

                                                                // Save the Google user's information under the "GoogleUser" node in the Realtime Database
                                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                databaseReference.child("User Profile").child("GoogleUser").child(userId).setValue(googleUser);

                                                                // Rest of your code here
                                                                // ...
                                                            }).addOnFailureListener(e -> {
                                                                // Handle failure to get download URL
                                                                dialog.dismiss();
                                                                Toast.makeText(LoginPage.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                                                            });
                                                        } else {
                                                            // Handle failure to upload profile picture
                                                            dialog.dismiss();
                                                            Toast.makeText(LoginPage.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            });
                                } else {
                                    // If profile picture URL is null, save User object without profile picture URL
                                    User googleUser = new User(userId, name, email, null, null); // Update User object without profile picture URL

                                    // Save the Google user's information under the "GoogleUser" node in the Realtime Database
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child("User Profile").child("GoogleUser").child(userId).setValue(googleUser);
                                }

                                // After successful login, check if this is the user's first time logging in
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference usersRef = databaseReference.child("User Profile").child("GoogleUser").child(userId);
                                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // User data exists in Firebase Realtime Database
                                            // Check if it's the first time the user is logging in
                                            Boolean isFirstTimeLogin = dataSnapshot.child("isFirstTimeLogin").getValue(Boolean.class);
                                            if (isFirstTimeLogin != null && isFirstTimeLogin) {
                                                // Redirect to preference activity
                                                Intent intent = new Intent(LoginPage.this, PreferencePage.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // Redirect to bottom navigation bar pages
                                                Intent intent = new Intent(LoginPage.this, BottomNavigationBar.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            // User data does not exist in Firebase Realtime Database
                                            Toast.makeText(LoginPage.this, "User data not found", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(LoginPage.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                // Save user's display name to SharedPreferences
                                SharedPreferences displayNamePreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = displayNamePreferences.edit();
                                editor.putString(userId, name); // use the user's ID as the key
                                editor.apply();

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
                            Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            assert user != null;

                            // create a new User object to store the Facebook user's name and email
                            User facebookUser = new User();
                            facebookUser.setUserId(user.getUid());
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

                            // Save the display name in shared preferences
                            SharedPreferences displayNamePreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                            displayNamePreferences.edit().putString(user.getUid(), user.getDisplayName()).apply(); // Store display name with user ID as the key

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