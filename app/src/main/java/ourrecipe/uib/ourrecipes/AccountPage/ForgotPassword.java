package ourrecipe.uib.ourrecipes.AccountPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import ourrecipe.uib.ourrecipes.R;

public class ForgotPassword extends AppCompatActivity {

    EditText forgetEmail;
    Button send;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_accountpage_forgot_password);

        forgetEmail = (EditText) findViewById(R.id.forgetEmail);
        send = (Button) findViewById(R.id.send);
        mAuth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        getSupportActionBar().hide();
    }

    private void resetPassword() {
        String email = forgetEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(ForgotPassword.this, "Enter Email", Toast.LENGTH_SHORT).show();
            forgetEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgetEmail.setError("Please Provide A Valid Email");
            forgetEmail.requestFocus();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(ForgotPassword.this, "Failed! "  + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }
}