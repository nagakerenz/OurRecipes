package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ourrecipe.uib.ourrecipes.R;

public class ChangePassword extends AppCompatActivity {
    Button save;

    EditText oldPassword ,password, confirmPassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountpage_change_password);

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




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChangePassword.this, "Saved Changes!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}