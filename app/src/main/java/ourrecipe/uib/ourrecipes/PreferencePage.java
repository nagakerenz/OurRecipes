package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PreferencePage extends AppCompatActivity {

    private boolean isBackPressedOnce = false;
    Button buttondone;
    Button buttonskip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_preference_page);

        buttondone = (Button) findViewById(R.id.doneButton);
        buttonskip = (Button) findViewById(R.id.skipButton);

        buttondone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });
        buttonskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });

        getSupportActionBar().hide();
    }
    public void openHomePage(){
        Intent homepage = new Intent(PreferencePage.this, BottomNavigationBar.class);
        startActivity(homepage);
    }

    @Override
    public void onBackPressed() {
        if (isBackPressedOnce){
            finishAffinity();
            finish();
            return;
        }

        Toast.makeText(PreferencePage.this, "Press Again To Exit Apps!", Toast.LENGTH_SHORT).show();
        isBackPressedOnce = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        },2000);
    }

}