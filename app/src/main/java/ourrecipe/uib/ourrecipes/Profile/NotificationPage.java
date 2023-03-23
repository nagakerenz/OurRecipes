package ourrecipe.uib.ourrecipes.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ourrecipe.uib.ourrecipes.R;

public class NotificationPage extends AppCompatActivity {
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_notification_page);

        reset = (Button) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NotificationPage.this, "Setting Reset!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}