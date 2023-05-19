package ourrecipe.uib.ourrecipes.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import ourrecipe.uib.ourrecipes.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class SearchResult extends AppCompatActivity {

    ImageButton menu;
    ImageButton menu1;
    ImageButton menu2;
    ImageButton menu3;
    ImageButton menu4;
    ImageButton menu5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_activity_search_result);

        menu = (ImageButton) findViewById(R.id.imageButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu1 = (ImageButton) findViewById(R.id.imageButton1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu2 = (ImageButton) findViewById(R.id.imageButton2);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu3 = (ImageButton) findViewById(R.id.imageButton3);
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu4 = (ImageButton) findViewById(R.id.imageButton4);
        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu5 = (ImageButton) findViewById(R.id.imageButton5);
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
    }

    public void openMenuPage() {
        Intent menu = new Intent(SearchResult.this, FoodRecipes.class);
        startActivity(menu);
    }
}