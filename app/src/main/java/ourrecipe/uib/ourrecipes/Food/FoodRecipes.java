package ourrecipe.uib.ourrecipes.Food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ourrecipe.uib.ourrecipes.R;

public class FoodRecipes extends AppCompatActivity {

    private ImageView foodImageView;
    private TextView categoriesTextView, nameTextView, timeTextView, calorieTextView, ratingTextView,
            servingSizeTextView, descriptionTextView, ingredientsTextView, stepsTextView;

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
        timeTextView = findViewById(R.id.timeTextView);
        calorieTextView = findViewById(R.id.calorieTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        servingSizeTextView = findViewById(R.id.servingSizeTextView);
        descriptionTextView = findViewById(R.id.foodDescription);
//        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        stepsTextView = findViewById(R.id.foodSteps);

        // Retrieve the parent category key and parent key from the intent
        Intent intent = getIntent();
        String parentCategoryKey = intent.getStringExtra("parentCategoryKey");
        String parentKey = intent.getStringExtra("parentKey");

        // Create a reference to the "Food Recipes" node in the database for the specified parent category key and parent key
        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("Food Recipes")
                .child(parentCategoryKey).child(parentKey);

        // Attach a ValueEventListener to fetch the food recipe data
        foodRecipesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve the food recipe data from the database
                String imageURL = dataSnapshot.child("imageURL").getValue(String.class);
                String name = dataSnapshot.child("name").getValue(String.class);
                Long times = dataSnapshot.child("times").getValue(Long.class);
                String rating = dataSnapshot.child("rating").getValue(String.class);
                String servingSize = dataSnapshot.child("servingSize").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
//                String ingredients = dataSnapshot.child("ingredients").getValue(String.class);
                String steps = dataSnapshot.child("steps").getValue(String.class);

                // Update the corresponding views in your layout with the retrieved data
                categoriesTextView.setText(parentCategoryKey);
                nameTextView.setText(name);
                timeTextView.setText(String.valueOf(times));
                ratingTextView.setText(rating);
                servingSizeTextView.setText(servingSize);
                descriptionTextView.setText(description);
//                ingredientsTextView.setText(ingredients);
                stepsTextView.setText(steps);

                // Load the image into the ImageView using an image loading library like Glide or Picasso
                // Use Glide library to load the image into the ImageButton
                Glide.with(FoodRecipes.this)
                        .load(imageURL)
                        .centerCrop()
                        .into(foodImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during data retrieval
            }
        };

        // Add the ValueEventListener to the recipe reference
        recipeRef.addListenerForSingleValueEvent(foodRecipesListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the activity is destroyed
        if (foodRecipesRef != null && foodRecipesListener != null) {
            foodRecipesRef.removeEventListener(foodRecipesListener);
        }
    }
}