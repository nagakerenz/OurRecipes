package ourrecipe.uib.ourrecipes.ui.reels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import ourrecipe.uib.ourrecipes.R;

public class EachVideo extends AppCompatActivity {
    CheckBox cbLove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reels_each_video);

        cbLove= (CheckBox) findViewById(R.id.cbLove);
        cbLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbLove.isChecked()) {
                    String getCBData = cbLove.getText().toString();
                    Toast.makeText(EachVideo.this, "Added to Your Favourite! " + getCBData, Toast.LENGTH_SHORT).show();
                }
                else if (!cbLove.isChecked()) {
                    String uncheckdata = cbLove.getText().toString();
                    Toast.makeText(EachVideo.this, "Removed from Your Favourite! " + uncheckdata, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}