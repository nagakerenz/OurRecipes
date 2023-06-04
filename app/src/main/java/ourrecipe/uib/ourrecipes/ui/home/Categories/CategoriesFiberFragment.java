package ourrecipe.uib.ourrecipes.ui.home.Categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class CategoriesFiberFragment extends Fragment {

    private RecyclerView recyclerView;
    private FoodIconRecyclerItemAdapter adapter;
    private DatabaseReference recipesRef;
    private ValueEventListener valueEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.d_fragment_categories_fiber, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewFiber);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));

        // Create an empty list for the data
        List<FoodIconRecipesDataClass> data = new ArrayList<>();

        // Create and set the adapter for the RecyclerView
        adapter = new FoodIconRecyclerItemAdapter(data);
        recyclerView.setAdapter(adapter);

        // Retrieve data from the Firebase Realtimes Database
        recipesRef = FirebaseDatabase.getInstance().getReference().child("Food Recipes").child("Fiber");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data before adding new items
                data.clear();

                // Iterate through the recipe snapshots
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    // Get the recipe details
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    Double rating = recipeSnapshot.child("rating").getValue(Double.class);
                    Long times = recipeSnapshot.child("times").getValue(Long.class);
                    String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);
                    Long liked = recipeSnapshot.child("liked").getValue(Long.class);
                    Boolean isFavorite = recipeSnapshot.child("isFavorite").getValue(Boolean.class);

                    // Retrieve the parentKey and parentCategoryKey from the snapshot's reference
                    String parentKey = recipeSnapshot.getRef().getParent().getKey();
                    String parentCategoryKey = recipeSnapshot.getRef().getParent().getParent().getKey();

                    // Add additional text to the "times" value
                    String timesText = times + " Minutes"; // Add " minutes" to the times value

                    // Create a Recipe object with the retrieved values
                    FoodIconRecipesDataClass recipe = new FoodIconRecipesDataClass(name, rating, times, imageURL, parentKey, parentCategoryKey, liked);

                    // Add the recipe to the list
                    data.add(recipe);
                }

                // Notify the adapter about the data change
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any
            }
        };
        recipesRef.addValueEventListener(valueEventListener);

        return view;
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