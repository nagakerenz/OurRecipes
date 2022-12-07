package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ourrecipe.uib.ourrecipes.ui.home.HomeFragment;

public class LunchPage extends AppCompatActivity {
    ImageButton back;
    ImageButton breakfast;
    ImageButton lunch;
    ImageButton dinner;
    ImageButton fiber;
    ImageButton drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_page);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHomepage();
            }
        });

        breakfast = (ImageButton) findViewById(R.id.breakfast);
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBreakfastPage();
            }
        });

        lunch = (ImageButton) findViewById(R.id.lunch);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLunchPage();
            }
        });
        dinner = (ImageButton) findViewById(R.id.dinner);
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDinnerPage();
            }
        });
        fiber = (ImageButton) findViewById(R.id.fiber);
        fiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFiberPage();
            }
        });
        drink = (ImageButton) findViewById(R.id.drink);
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrinkPage();
            }
        });
    }

    public void backToHomepage(){
        Intent back = new Intent(this, HomeFragment.class);
        startActivity(back);
    }

    public void openBreakfastPage() {
        Intent breakfast = new Intent(this, BreakfastPage.class);
        startActivity(breakfast);
    }

    public void openLunchPage(){
        Intent lunch = new Intent(this, LunchPage.class);
        startActivity(lunch);
    }

    public void openDinnerPage(){
        Intent dinner = new Intent(this, DinnerPage.class);
        startActivity(dinner);
    }

    public void openFiberPage(){
        Intent fiber = new Intent(this, FiberPage.class);
        startActivity(fiber);
    }

    public void openDrinkPage() {
        Intent drink = new Intent(this, DrinkPage.class);
        startActivity(drink);
    }
}