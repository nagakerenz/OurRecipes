package ourrecipe.uib.ourrecipes.Food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ourrecipe.uib.ourrecipes.AccountPage.LoginPage;
import ourrecipe.uib.ourrecipes.Food.viewPager2.IngredientAndStepAdapter;
import ourrecipe.uib.ourrecipes.Food.viewPager2.IngredientFragmentDataClass;
import ourrecipe.uib.ourrecipes.Food.viewPager2.IngredientRecyclerViewAdapter;
import ourrecipe.uib.ourrecipes.Food.viewPager2.StepFragment;
import ourrecipe.uib.ourrecipes.R;

public class FoodRecipes extends AppCompatActivity {

    private ImageView foodImageView;
    private ImageButton likeImageButton;
    private TextView likedTextView, categoriesTextView, nameTextView, timeTextView, calorieTextView, ratingTextView,
            servingSizeTextView, descriptionTextView, ingredientsTextView, stepsTextView;

    private DatabaseReference foodRecipesRef;
    private ValueEventListener foodRecipesListener;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private IngredientAndStepAdapter adapter;
    private List<IngredientFragmentDataClass> ingredientsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_activity_food_recipes);

        // Initialize views
        likedTextView = findViewById(R.id.foodLiked);
        likeImageButton = findViewById(R.id.likeButton);
        categoriesTextView = findViewById(R.id.foodCategories);
        foodImageView = findViewById(R.id.foodImage);
        nameTextView = findViewById(R.id.foodName);
        timeTextView = findViewById(R.id.timeTextView);
        calorieTextView = findViewById(R.id.calorieTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        servingSizeTextView = findViewById(R.id.servingSizeTextView);
        descriptionTextView = findViewById(R.id.foodDescription);
        stepsTextView = findViewById(R.id.foodSteps);
//        tabLayout = findViewById(R.id.tabLayout);
//        viewPager2 = findViewById(R.id.viewPager2StepAndIngredient);

        //This is for Handling the Steps and Ingredients
//        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
//        tabLayout.addTab(tabLayout.newTab().setText("Steps"));

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });
        AlertDialog.Builder builder = new AlertDialog.Builder(FoodRecipes.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        //THIS IS FOR HANDLING THE DISPLAYING DATA AND RETREIVING DATA
        // Retrieve the parent category key and parent key from the intent
        Intent intent = getIntent();
        String parentKey = intent.getStringExtra("parentKey");
        String childKey = intent.getStringExtra("childKey");
        String id = intent.getStringExtra("id"); // Retrieve the "id" value from the intent extras
        String foodId = intent.getStringExtra("foodId"); // Retrieve the "id" value from the intent extras

        // Create a reference to the "Food Recipes" node in the database for the specified parent category key and parent key
        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("Food Recipes");

        // Attach a ValueEventListener to fetch the food recipe data
        foodRecipesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot recipeSnapshot : categorySnapshot.getChildren()) {
                        String recipeId = recipeSnapshot.child("id").getValue(String.class);
                        if (recipeId != null && recipeId.equals(id)) {
                            dialog.dismiss();

                            // Retrieve the food recipe data from the database
                            String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);
                            String name = recipeSnapshot.child("name").getValue(String.class);
                            String description = recipeSnapshot.child("description").getValue(String.class);
                            Long times = recipeSnapshot.child("times").getValue(Long.class);
                            Double calorie = recipeSnapshot.child("calories").getValue(Double.class);
                            Double rating = recipeSnapshot.child("rating").getValue(Double.class);
                            Long servingSize = recipeSnapshot.child("servingSize").getValue(Long.class);

                            //Handling the Like button
                            Long liked = recipeSnapshot.child("liked").getValue(Long.class);
                            List<String> likedUser = new ArrayList<>();
                            for (DataSnapshot likedUserSnapshot : recipeSnapshot.child("likedUser").getChildren()) {
                                likedUser.add(likedUserSnapshot.getValue(String.class));
                            }
                            // DatabaseReference for the "liked" value
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = currentUser.getUid();
                            DatabaseReference likedReference = FirebaseDatabase.getInstance().getReference("Food Recipes")
                                    .child(parentKey)
                                    .child(childKey)
                                    .child("liked");

                            // ValueEventListener to update the "liked" display
                            likedReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Long liked = dataSnapshot.getValue(Long.class);
                                    if (liked != null) {
                                        likedTextView.setText(String.valueOf(liked));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle error
                                }
                            });

                            // Check if the current user's ID exists in the likedUser list
                            boolean isLiked = likedUser.contains(userId);
                            // Update the state and image resource of the likeButton
                            if (isLiked) {
                                likeImageButton.setSelected(true);
                                likeImageButton.setImageResource(R.drawable.f_food_recipe_icon_favorite_button_red);
                            } else {
                                likeImageButton.setSelected(false);
                                likeImageButton.setImageResource(R.drawable.f_food_recipe_icon_favorite_button_white);
                            }

                            //HANDLING THE LIKE BUTTON
                            likeImageButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String provider = "";

                                    for (UserInfo userInfo : currentUser.getProviderData()) {
                                        if (userInfo.getProviderId().equals("facebook.com")) {
                                            provider = "FacebookUser";
                                            break;
                                        } else if (userInfo.getProviderId().equals("google.com")) {
                                            provider = "GoogleUser";
                                            break;
                                        }
                                    }

                                    if (provider.isEmpty()) {
                                        provider = "User";
                                    }

                                    // Update the state and image resource of the likeButton
                                    boolean isChecked = !likeImageButton.isSelected();
                                    likeImageButton.setSelected(isChecked);
                                    int likeButtonImageResource = isChecked ? R.drawable.f_food_recipe_icon_favorite_button_red : R.drawable.f_food_recipe_icon_favorite_button_white;
                                    likeImageButton.setImageResource(likeButtonImageResource);

                                    DatabaseReference databaseReferenceId = FirebaseDatabase.getInstance().getReference("Food Recipes")
                                            .child(parentKey)
                                            .child(childKey)
                                            .child("id");

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food Recipes")
                                            .child(parentKey)
                                            .child(childKey)
                                            .child("liked");

                                    DatabaseReference likedUserReference = FirebaseDatabase.getInstance().getReference("Food Recipes")
                                            .child(parentKey)
                                            .child(childKey)
                                            .child("likedUser");

                                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User Profile")
                                            .child(provider)
                                            .child(userId)
                                            .child("favoriteRecipes");
                                    likedReference.runTransaction(new Transaction.Handler() {
                                        @Override
                                        public Transaction.Result doTransaction(MutableData mutableData) {
                                            Long currentLiked = mutableData.getValue(Long.class);
                                            if (currentLiked == null) {
                                                currentLiked = 0L;
                                            }
                                            // Adjust the liked count based on the state of the likeButton
                                            if (isChecked) {
                                                currentLiked++;
                                            } else {
                                                currentLiked--;
                                            }
                                            mutableData.setValue(currentLiked);
                                            return Transaction.success(mutableData);
                                        }

                                        @Override
                                        public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                                            // Transaction completed
                                            if (!committed) {
                                                // Handle transaction error
                                                // You can show an error message or perform appropriate actions
                                            } else {
                                                DatabaseReference likedUserReference = FirebaseDatabase.getInstance().getReference("Food Recipes")
                                                        .child(parentKey)
                                                        .child(childKey)
                                                        .child("likedUser");

                                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                if (isChecked) {
                                                    likedUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            long count = snapshot.getChildrenCount(); // Get the current child count
                                                            likedUserReference.child(String.valueOf(count)).setValue(userId);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {
                                                            // Handle error
                                                        }
                                                    });

                                                    // Retrieve the "ID" value from "Food Recipes"
                                                    databaseReferenceId.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            String foodId = dataSnapshot.getValue(String.class);
                                                            if (foodId != null) {
                                                                // Store the "ID" value under the user's favoriteRecipes
                                                                DatabaseReference newFavoriteRef = userReference.child(foodId);
                                                                newFavoriteRef.child("category").setValue(parentKey);
                                                                newFavoriteRef.child("position").setValue(childKey);
                                                                newFavoriteRef.child("id").setValue(foodId);

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            // Handle error
                                                        }
                                                    });
                                                } else {
                                                    likedUserReference.orderByValue().equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                                                childSnapshot.getRef().removeValue();
                                                                break;
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {
                                                            // Handle error
                                                        }
                                                    });

                                                    // Find the favorite recipe with the specific category and ID, and remove it
                                                    userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                                String category = childSnapshot.child("category").getValue(String.class);
                                                                String position = childSnapshot.child("position").getValue(String.class);

                                                                if (category != null && category.equals(parentKey) && position != null && position.equals(childKey)) {
                                                                    childSnapshot.getRef().removeValue(); // Remove the favorite recipe
                                                                    break;
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            // Handle error
                                                        }
                                                    });
                                                }
                                                // Update the likedTextView if needed
                                                likedTextView.setText(String.valueOf(dataSnapshot.getValue(Long.class)));
                                            }
                                        }
                                    });
                                }
                            });

                            //Handling Steps and Ingredients
                            List<String> steps = recipeSnapshot.child("steps").getValue(new GenericTypeIndicator<List<String>>() {
                            });
                            ingredientsList = new ArrayList<>();
                            DataSnapshot ingredientsSnapshot = recipeSnapshot.child("ingredients");
                            for (DataSnapshot ingredientSnapshot : ingredientsSnapshot.getChildren()) {
                                String ingredientImage = ingredientSnapshot.child("image").getValue(String.class);
                                String ingredientName = ingredientSnapshot.child("name").getValue(String.class);
                                String ingredientPortion = ingredientSnapshot.child("portion").getValue(String.class);
                                IngredientFragmentDataClass ingredient = new IngredientFragmentDataClass(ingredientImage, ingredientName, ingredientPortion);
                                ingredientsList.add(ingredient);
                            }

                            // Update the corresponding views in your layout with the retrieved data
                            categoriesTextView.setText(parentKey);
                            likedTextView.setText(String.valueOf(liked)); // Append " minutes" after the value
                            nameTextView.setText(name);
                            descriptionTextView.setText(description);
                            timeTextView.setText(String.valueOf(times) + " Minutes"); // Append " minutes" after the value
                            calorieTextView.setText(String.valueOf(calorie) + " Calories"); // Append " calorie" after the value
                            ratingTextView.setText(String.valueOf(rating));
                            servingSizeTextView.setText(String.valueOf(servingSize) + " Serving"); // Append " serving" after the value

                            //FOR HANDLING THE INGREDIENTS
                            RecyclerView recyclerView = findViewById(R.id.recyclerViewIngredients);
                            recyclerView.setLayoutManager(new LinearLayoutManager(FoodRecipes.this, LinearLayoutManager.HORIZONTAL, false));
                            // Initialize and set up the RecyclerView adapter
                            IngredientRecyclerViewAdapter adapter = new IngredientRecyclerViewAdapter(ingredientsList);
                            recyclerView.setAdapter(adapter);

                            //FOR HANDLING STEPS
                            StringBuilder stepsStringBuilder = new StringBuilder();
                            for (int i = 0; i < steps.size(); i++) {
                                String step = steps.get(i);
                                int stepNumber = i + 1;
                                stepsStringBuilder.append(stepNumber).append(". ").append(step).append("\n");
                            }
                            String stepsText = stepsStringBuilder.toString();
                            stepsTextView.setText(stepsText);

//                    adapter = new IngredientAndStepAdapter(getSupportFragmentManager(), getLifecycle(), ingredientsList, steps);

                            // Use Glide library to load the image into the ImageButton
                            Glide.with(FoodRecipes.this)
                                    .load(imageURL)
                                    .centerCrop()
                                    .into(foodImageView);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during data retrieval
                dialog.dismiss();

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