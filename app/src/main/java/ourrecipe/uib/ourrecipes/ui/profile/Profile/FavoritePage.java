package ourrecipe.uib.ourrecipes.ui.profile.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodIconRecipesDataClass;
import ourrecipe.uib.ourrecipes.Food.FoodIconRecyclerItemAdapter;
import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class FavoritePage extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FoodIconRecyclerItemAdapter adapter;
    private DatabaseReference recipesRef;
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
        adapter = new FoodIconRecyclerItemAdapter(data);
        recyclerView.setAdapter(adapter);

        // Retrieve data from the Firebase Realtimes Database
        recipesRef = FirebaseDatabase.getInstance().getReference().child("Food Recipes");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data before adding new items
                data.clear();
                progressBar.setVisibility(View.VISIBLE);

                // Retrieve the current user's ID
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();

                // Iterate through the recipe snapshots
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot recipeSnapshot : categorySnapshot.getChildren()) {
                        // Check if the likedUser node exists and if the current user's ID exists in it
                        if (recipeSnapshot.child("likedUser").exists()) {
//                            if (recipeSnapshot.child("likedUser").exists() && recipeSnapshot.child("likedUser").hasChild(userId)) {
                                // Get the recipe details
                            String name = recipeSnapshot.child("name").getValue(String.class);
                            Double rating = recipeSnapshot.child("rating").getValue(Double.class);
                            Long times = recipeSnapshot.child("times").getValue(Long.class);
                            String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);
                            Long liked = recipeSnapshot.child("liked").getValue(Long.class);
                            List<String> likedUser = new ArrayList<>();
                            for (DataSnapshot likedUserSnapshot : recipeSnapshot.child("likedUser").getChildren()) {
                                likedUser.add(likedUserSnapshot.getValue(String.class));
                            }
                            // Check if the current user's ID exists in the likedUser list
                            boolean isLiked = likedUser.contains(userId);

                            // Retrieve the parentKey and parentCategoryKey from the snapshot's reference
                            String parentKey = recipeSnapshot.getRef().getParent().getKey();
                            String parentCategoryKey = recipeSnapshot.getRef().getParent().getParent().getKey();

                            // Add additional text to the "times" value
                            String timesText = times + " Minutes"; // Add " minutes" to the times value

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
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        };
        recipesRef.addListenerForSingleValueEvent(valueEventListener);

        getSupportActionBar().hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the fragment is destroyed
        if (recipesRef != null && valueEventListener != null) {
            recipesRef.removeEventListener(valueEventListener);
        }
    }
}