package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ourrecipe.uib.ourrecipes.R;

public class PChangeEmail extends AppCompatActivity {
    private EditText oldEmailEditText;
    private EditText newEmailEditText;
    private Button saveChangesButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userRef; // Reference to the user's data in the Realtime Database

    EditText passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_accountpage_change_email);


        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        userRef = FirebaseDatabase.getInstance()
                .getReference("User Profile")
                .child("User")
                .child(userId)
                .child("userInfo");

        // Find views
        oldEmailEditText = findViewById(R.id.name);
        newEmailEditText = findViewById(R.id.email);
        saveChangesButton = findViewById(R.id.save);
        progressBar = findViewById(R.id.loading);

        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);


        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (passwordEditText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    passwordEditText.setSelection(passwordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });

        confirmPasswordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (confirmPasswordEditText.getRight() - confirmPasswordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (confirmPasswordEditText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        confirmPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        confirmPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    confirmPasswordEditText.setSelection(confirmPasswordEditText.getText().length());
                    return true;
                }
            }
            return false;
        });

        // Set click listener for save button
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
        getSupportActionBar().hide();
    }

    private void saveChanges() {
        // Get input values
        String oldEmail = oldEmailEditText.getText().toString().trim();
        String newEmail = newEmailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate input
        if (oldEmail.isEmpty() || newEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Re-authenticate user with current email and password
        firebaseAuth.signInWithEmailAndPassword(oldEmail, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User re-authenticated successfully
                        updateEmail(newEmail);
                    } else {
                        // Re-authentication failed
                        Toast.makeText(PChangeEmail.this, "Failed to re-authenticate. Please check your credentials.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void updateEmail(String newEmail) {
        firebaseUser.updateEmail(newEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update the email in the Realtime Database as well
                        userRef.child("email").setValue(newEmail)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(PChangeEmail.this, "Email updated successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(PChangeEmail.this, "Failed to update email in the database", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            });

                    } else {
                        // Failed to update email
                        Toast.makeText(PChangeEmail.this, "Failed to update email. Please try again.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}