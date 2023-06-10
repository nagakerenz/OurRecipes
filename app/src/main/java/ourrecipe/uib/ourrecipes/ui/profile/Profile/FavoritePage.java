package ourrecipe.uib.ourrecipes.ui.profile.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodIconRecipesDataClass;
import ourrecipe.uib.ourrecipes.Food.FoodIconRecyclerItemAdapter;
import ourrecipe.uib.ourrecipes.R;


public class FavoritePage extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FavoritePageFoodIconRecyclerItemAdapter adapter;
    private DatabaseReference userReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_profile_favorite_page);

        progressBar = findViewById(R.id.progressBar);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewFavorite);
        recyclerView.setLayoutManager(new GridLayoutManager(FavoritePage.this, 2));

        // Create an empty list for the data
        List<FoodIconRecipesDataClass> data = new ArrayList<>();

        // Create and set the adapter for the RecyclerView
        adapter = new FavoritePageFoodIconRecyclerItemAdapter(data);
        recyclerView.setAdapter(adapter);

        // Retrieve the current user's ID and provider
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
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
        // Retrieve the user's favoriteRecipes data
        userReference = FirebaseDatabase.getInstance().getReference("User Profile")
                .child(provider)
                .child(userId)
                .child("favoriteRecipes");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data before adding new items
                data.clear();
                progressBar.setVisibility(View.VISIBLE);

                if (dataSnapshot.exists()) {

                    for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                        String favoriteRecipeId = recipeSnapshot.child("id").getValue(String.class);
                        if (favoriteRecipeId != null) {
                            // Find the matching recipe in "Food Recipes" based on the retrieved "id"
                            DatabaseReference foodRecipesRef = FirebaseDatabase.getInstance().getReference("Food Recipes");
                            foodRecipesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                                        String category = categorySnapshot.getKey(); // e.g., "Breakfast", "Dinner"
                                        for (DataSnapshot recipeSnapshot : categorySnapshot.getChildren()) {
                                            String arrayID = recipeSnapshot.getKey(); // e.g., "Breakfast", "Dinner"
                                            String recipeId = recipeSnapshot.child("id").getValue(String.class);
                                            if (favoriteRecipeId.equals(recipeId)) {
                                                // Get the recipe details
                                                String name = recipeSnapshot.child("name").getValue(String.class);
                                                Double rating = recipeSnapshot.child("rating").getValue(Double.class);
                                                Long times = recipeSnapshot.child("n_steps").getValue(Long.class);
                                                String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);
                                                Long liked = recipeSnapshot.child("liked").getValue(Long.class);
                                                List<String> likedUser = new ArrayList<>();
                                                for (DataSnapshot likedUserSnapshot : recipeSnapshot.child("likedUser").getChildren()) {
                                                    likedUser.add(likedUserSnapshot.getValue(String.class));
                                                }
                                                boolean isLiked = likedUser.contains(userId);

                                                // Retrieve the parentKey and parentCategoryKey from the snapshot's reference
                                                String parentKey = recipeSnapshot.getRef().getParent().getKey();
                                                String parentCategoryKey = recipeSnapshot.getRef().getParent().getParent().getKey();

                                                // Create a Recipe object with the retrieved values
                                                FoodIconRecipesDataClass recipe = new FoodIconRecipesDataClass(name, rating, times, imageURL, parentKey, parentCategoryKey, liked, likedUser, isLiked);
                                                // Add the recipe to the list
                                                data.add(recipe);
                                            }
                                        }
                                    }

                                    // Notify the adapter about the data change
                                    adapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(FavoritePage.this, "Failed to retrieve favorite recipes.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                } else {
                    // No data available, show a toast message
                    Toast.makeText(FavoritePage.this, "Go Like Something", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FavoritePage.this, "Failed to retrieve favorite recipes.", Toast.LENGTH_SHORT).show();
            }
        };

        userReference.addListenerForSingleValueEvent(valueEventListener);

        getSupportActionBar().hide();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the activity is destroyed
        if (userReference != null && valueEventListener != null) {
            userReference.removeEventListener(valueEventListener);
        }
    }
}