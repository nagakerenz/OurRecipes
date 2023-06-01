package ourrecipe.uib.ourrecipes.Food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ourrecipe.uib.ourrecipes.Food.viewPager2.IngredientAndStepAdapter;
import ourrecipe.uib.ourrecipes.R;

public class FoodRecipes extends AppCompatActivity {

    private ImageView foodImageView;
    private TextView categoriesTextView, nameTextView, timeTextView, calorieTextView, ratingTextView,
            servingSizeTextView, descriptionTextView, ingredientsTextView, stepsTextView;

    private DatabaseReference foodRecipesRef;
    private ValueEventListener foodRecipesListener;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private IngredientAndStepAdapter adapter;

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
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2StepAndIngredient);

        //This is for Handling the Steps and Ingredients
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Steps"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new IngredientAndStepAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        // Set the custom selected color for the TabLayout
//        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_selected_text_color));
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_selected_background_color));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        //THIS IS FOR HANDLING THE DISPLAYING DATA AND RETREIVING DATA
        // Retrieve the parent category key and parent key from the intent
        Intent intent = getIntent();
        String parentKey = intent.getStringExtra("parentKey");
        String childKey = intent.getStringExtra("childKey");

        // Show the received ID in a Toast message
        Toast.makeText(this, "parentKey: " + parentKey, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "childKey: " + childKey, Toast.LENGTH_SHORT).show();

        // Create a reference to the "Food Recipes" node in the database for the specified parent category key and parent key
        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("Food Recipes")
                .child(parentKey).child(childKey);

        // Attach a ValueEventListener to fetch the food recipe data

        foodRecipesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

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
                    categoriesTextView.setText(parentKey);
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
                } else {
                    Toast.makeText(FoodRecipes.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during data retrieval
            }
        };

        // Add the ValueEventListener to the recipe reference
        recipeRef.addListenerForSingleValueEvent(foodRecipesListener);

        getSupportActionBar().hide();


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