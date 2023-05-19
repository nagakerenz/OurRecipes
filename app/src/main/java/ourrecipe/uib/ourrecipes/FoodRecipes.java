package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodRecipes extends AppCompatActivity {

    private ImageView foodImageView;
    private TextView categoriesTextView, nameTextView, timeTextView, calorieTextView, ratingTextView, servingSizeTextView, descriptionTextView, ingredientsTextView, stepsTextView;

    private DatabaseReference foodRecipesRef;
    private ValueEventListener foodRecipesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_activity_food_recipes);

        // Initialize views
        categoriesTextView = findViewById(R.id.foodCategories);
        foodImageView = findViewById(R.id.foodImage);
        nameTextView = findViewById(R.id.foodName);
//        timeTextView = findViewById(R.id.timeTextView);
//        calorieTextView = findViewById(R.id.calorieTextView);
//        ratingTextView = findViewById(R.id.ratingTextView);
//        servingSizeTextView = findViewById(R.id.servingSizeTextView);
        descriptionTextView = findViewById(R.id.foodDescription);
//        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        stepsTextView = findViewById(R.id.foodSteps);

        // Retrieve the recipe ID and category from the intent
        Intent intent = getIntent();
        String recipeId = intent.getStringExtra("recipeId");
        String category = intent.getStringExtra("category");


        // Create a reference to the "Food Recipes" node in the database for the specified recipe ID
        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("Food Recipes").child(recipeId);

        // Attach a ValueEventListener to fetch the category for the recipe ID
        ValueEventListener categoryListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve the category for the recipe ID from the database
                String category = dataSnapshot.child("category").getValue(String.class);

                // Create a reference to the "Food Recipes" node in the database for the retrieved category and recipe ID
                foodRecipesRef = FirebaseDatabase.getInstance().getReference("Food Recipes").child(category).child(recipeId);

                // Attach a ValueEventListener to fetch the food recipe data
                foodRecipesListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Retrieve the food recipe data from the database
                        String categories = dataSnapshot.child("category").getValue(String.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String time = dataSnapshot.child("minutes").getValue(String.class);
                        String calorie = dataSnapshot.child("nutrition").child("calorie").getValue(String.class);
                        String rating = dataSnapshot.child("nutrition").child("rating").getValue(String.class);
                        String servingSize = dataSnapshot.child("nutrition").child("serving_size").getValue(String.class);
                        String description = dataSnapshot.child("description").getValue(String.class);
                        String ingredients = dataSnapshot.child("ingredients").getValue(String.class);
                        String steps = dataSnapshot.child("steps").getValue(String.class);

                        // Update the corresponding views in your layout with the retrieved data
                        categoriesTextView.setText(categories);
                        nameTextView.setText(name);
                        timeTextView.setText(time);
                        calorieTextView.setText(calorie);
                        ratingTextView.setText(rating);
                        servingSizeTextView.setText(servingSize);
                        descriptionTextView.setText(description);
                        ingredientsTextView.setText(ingredients);
                        stepsTextView.setText(steps);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle any errors that occur during data retrieval
                    }
                };

                // Add the ValueEventListener to the food recipes reference
                foodRecipesRef.addListenerForSingleValueEvent(foodRecipesListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during data retrieval
            }
        };

        // Add the ValueEventListener to the recipe reference
        recipeRef.addListenerForSingleValueEvent(categoryListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the activity is destroyed
        foodRecipesRef.removeEventListener(foodRecipesListener);
    }
}