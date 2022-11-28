package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageButton dinner = (ImageButton) findViewById(R.id.dinner);
        ImageButton breakfast = (ImageButton) findViewById(R.id.breakfast);
        ImageButton lunch = (ImageButton) findViewById(R.id.lunch);
        ImageButton fiber = (ImageButton) findViewById(R.id.fiber);
        ImageButton drink = (ImageButton) findViewById(R.id.drink);

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dinner=new Intent(HomePage.this, DinnerPage.class);
                startActivity(dinner);
            }
        });
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent breakfast=new Intent(HomePage.this, BreakfastPage.class);
                startActivity(breakfast);
            }
        });
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lunch=new Intent(HomePage.this, LunchPage.class);
                startActivity(lunch);
            }
        });
        fiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fiber=new Intent(HomePage.this, FiberPage.class);
                startActivity(fiber);
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent drink=new Intent(HomePage.this, DrinkPage.class);
                startActivity(drink);
            }
        });
    }
}