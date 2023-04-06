package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import ourrecipe.uib.ourrecipes.BottomNavigationBar;
import ourrecipe.uib.ourrecipes.PreferencePage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.ui.profile.ProfileFragment;

public class SignUpPage extends AppCompatActivity {

    EditText signUpName, signUpAge, signUpEmail, signUpPassword, signUpConfirmPassword;
    Button signUp;
    Button login;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    private Calendar selectedDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_signup_page);

        mAuth = FirebaseAuth.getInstance();
        signUpName = findViewById(R.id.name);
        signUpAge = findViewById(R.id.birthDate);
        signUpEmail = findViewById(R.id.email);
        signUpPassword = findViewById(R.id.password);
        signUpConfirmPassword = findViewById(R.id.confirmPassword);
        signUp = findViewById(R.id.signUp);

        signUpAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, age, email, password, confirmPassword;
                name = String.valueOf(signUpName.getText().toString().trim());
                age = String.valueOf(signUpAge.getText().toString().trim());
                email = String.valueOf(signUpEmail.getText().toString().trim());
                password = String.valueOf(signUpPassword.getText().toString().trim());
                confirmPassword = String.valueOf(signUpConfirmPassword.getText().toString().trim());

                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(SignUpPage.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    signUpName.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(age)) {
                    Toast.makeText(SignUpPage.this, "Enter Your BirthDate", Toast.LENGTH_SHORT).show();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();

                            String userId = mAuth.getCurrentUser().getUid();
                            User user = new User(userId, name, email, null);

                            // Check if a date has been selected
                            if (selectedDate != null) {
                                // Convert birth date to string for storage in Firebase
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String birthDateString = sdf.format(selectedDate.getTime());
                                user.setBirthDate(birthDateString);
                            } else {
                                Toast.makeText(SignUpPage.this, "Enter Your Birth Date", Toast.LENGTH_SHORT).show();
                                signUpAge.requestFocus();
                                return;
                            }

                            FirebaseDatabase.getInstance().getReference("User Profile").child("User").child(userId)
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        dialog.dismiss();
                                        Toast.makeText(SignUpPage.this, "SignUp Successful.",
                                                Toast.LENGTH_SHORT).show();
                                        // After successful sign up, go back to login page
                                        startActivity(new Intent(SignUpPage.this, LoginPage.class));
                                        finish(); // prevent the user from returning to the sign up activity via the back button
                                    } else {
                                        dialog.dismiss();
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUpPage.this, "SignUp Failed. " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            // Store user name in SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("name", name);
                            editor.apply();

                        } else {
                            dialog.dismiss();
                            Toast.makeText(SignUpPage.this, "SignUp Failed. " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signUpPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (signUpPassword.getRight() - signUpPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (signUpPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        signUpPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        signUpPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        signUpPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        signUpPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    signUpPassword.setSelection(signUpPassword.getText().length());
                    return true;
                }
            }
            return false;
        });

        signUpConfirmPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (signUpConfirmPassword.getRight() - signUpConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (signUpConfirmPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        signUpConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        signUpConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        signUpConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        signUpConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    signUpConfirmPassword.setSelection(signUpConfirmPassword.getText().length());
                    return true;
                }
            }
            return false;
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

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Store the selected date
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        // Set the selected date to the EditText
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = sdf.format(selectedDate.getTime());
                        signUpAge.setText(formattedDate);
                    }
                },
                year, month, day
        );

        // Set the maximum date to today's date
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();

        // If a date has already been selected, set the DatePickerDialog to that date
        if (selectedDate != null) {
            datePickerDialog.updateDate(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        }
    }



    public void openLogin() {
        Intent login = new Intent(SignUpPage.this, LoginPage.class);
        startActivity(login);
    }
}