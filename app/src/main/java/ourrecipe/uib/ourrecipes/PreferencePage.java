package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreferencePage extends AppCompatActivity {
    Button buttondone;
    Button buttonskip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_page);

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

}