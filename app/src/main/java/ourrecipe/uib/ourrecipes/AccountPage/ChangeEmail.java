package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ourrecipe.uib.ourrecipes.R;

public class ChangeEmail extends AppCompatActivity {
    Button save;
    EditText password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_accountpage_change_email);


        save = (Button) findViewById(R.id.save);

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
                Toast.makeText(ChangeEmail.this, "Saved Changes!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}