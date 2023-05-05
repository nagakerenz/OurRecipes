package ourrecipe.uib.ourrecipes;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView nameTextView;
    private TextView timeTextView;
    private TextView calorieTextView;
    private TextView ratingTextView;
    private TextView servingSizeTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private TextView stepsTextView;

    private DatabaseReference foodRecipesRef;
    private ValueEventListener foodRecipesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipes);

        // Initialize views
        foodImageView = findViewById(R.id.foodImage);
        nameTextView = findViewById(R.id.foodName);
//        timeTextView = findViewById(R.id.timeTextView);
//        calorieTextView = findViewById(R.id.calorieTextView);
//        ratingTextView = findViewById(R.id.ratingTextView);
//        servingSizeTextView = findViewById(R.id.servingSizeTextView);
        descriptionTextView = findViewById(R.id.foodDescription);
//        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        stepsTextView = findViewById(R.id.foodSteps);

        // Create a reference to the "Food Recipes" node in the database
        foodRecipesRef = FirebaseDatabase.getInstance().getReference("Food Recipes").child("Dinner").child("0");

        // Attach a ValueEventListener to fetch the food recipe data
        foodRecipesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve the food recipe data from the database
                String name = dataSnapshot.child("name").getValue(String.class);
                String time = dataSnapshot.child("minutes").getValue(String.class);
                String calorie = dataSnapshot.child("nutrition").child("calorie").getValue(String.class);
                String rating = dataSnapshot.child("nutrition").child("rating").getValue(String.class);
                String servingSize = dataSnapshot.child("nutrition").child("serving_size").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
                String ingredients = dataSnapshot.child("ingredients").getValue(String.class);
                String steps = dataSnapshot.child("steps").getValue(String.class);

                // Update the corresponding views in your layout with the retrieved data
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
    protected void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the activity is destroyed
        foodRecipesRef.removeEventListener(foodRecipesListener);
    }
}