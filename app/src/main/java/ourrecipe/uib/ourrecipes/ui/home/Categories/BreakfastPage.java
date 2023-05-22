package ourrecipe.uib.ourrecipes.ui.home.Categories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class BreakfastPage extends AppCompatActivity {

    ImageButton breakfast;
    ImageButton lunch;
    ImageButton dinner;
    ImageButton fiber;
    ImageButton drink;
    ImageButton menu;
    ImageButton menu1;
    ImageButton menu2;
    ImageButton menu3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodpage_breakfast_page);

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
    public void openMenuPage() {
        Intent menu = new Intent(BreakfastPage.this, FoodRecipes.class);
        startActivity(menu);
    }
}