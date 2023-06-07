package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ourrecipe.uib.ourrecipes.R;

public class PChangePassword extends AppCompatActivity {
    Button save;

    EditText oldPassword ,password, confirmPassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_accountpage_change_password);

        oldPassword = findViewById(R.id.oldPassword);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        save = (Button) findViewById(R.id.save);


        oldPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (oldPassword.getRight() - oldPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (oldPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        oldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        oldPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        oldPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    oldPassword.setSelection(oldPassword.getText().length());
                    return true;
                }
            }
            return false;
        });

        password.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (password.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    password.setSelection(password.getText().length());
                    return true;
                }
            }
            return false;
        });

        confirmPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (confirmPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                        confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                    } else {
                        confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                    }
                    confirmPassword.setSelection(confirmPassword.getText().length());
                    return true;
                }
            }
            return false;
        });

        save.setOnClickListener(view -> {
            String oldPasswordText = oldPassword.getText().toString().trim();
            String newPasswordText = password.getText().toString().trim();
            String confirmPasswordText = confirmPassword.getText().toString().trim();

            if (newPasswordText.length() < 6) {
                Toast.makeText(PChangePassword.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            } else if (!newPasswordText.equals(confirmPasswordText)) {
                Toast.makeText(PChangePassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Get the current user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Create credential for re-authentication
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPasswordText);

                // Re-authenticate the user
                user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // User has been successfully re-authenticated, now change the password
                            user.updatePassword(newPasswordText)
                                    .addOnCompleteListener(passwordUpdateTask -> {
                                        if (passwordUpdateTask.isSuccessful()) {
                                            // Password has been successfully updated
                                            Toast.makeText(PChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Password update failed
                                            Toast.makeText(PChangePassword.this, "Failed to change password. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Re-authentication failed
                            Toast.makeText(PChangePassword.this, "Invalid old password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        getSupportActionBar().hide();
    }
}