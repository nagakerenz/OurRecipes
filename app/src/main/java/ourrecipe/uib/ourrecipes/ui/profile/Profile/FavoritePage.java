package ourrecipe.uib.ourrecipes.ui.profile.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class FavoritePage extends AppCompatActivity {

    ImageButton menu;
    ImageButton menu1;
    ImageButton menu2;
    ImageButton menu3;
    ImageButton menu4;
    ImageButton menu5;
    ImageButton menu6;
    ImageButton menu7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_profile_favorite_page);

        menu = (ImageButton) findViewById(R.id.imageButton);
        menu1 = (ImageButton) findViewById(R.id.imageButton1);
        menu2 = (ImageButton) findViewById(R.id.imageButton2);
        menu3 = (ImageButton) findViewById(R.id.imageButton3);
        menu4 = (ImageButton) findViewById(R.id.imageButton4);
        menu5 = (ImageButton) findViewById(R.id.imageButton5);
        menu6 = (ImageButton) findViewById(R.id.imageButton6);
        menu7 = (ImageButton) findViewById(R.id.imageButton7);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
    }

    public void openMenuPage() {
        Intent menu = new Intent(FavoritePage.this, FoodRecipes.class);
        startActivity(menu);
    }
}