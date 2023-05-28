package ourrecipe.uib.ourrecipes.ui.home.Categories;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodRecipesIconDataClass;
import ourrecipe.uib.ourrecipes.Food.FoodRecyclerItemAdapter;
import ourrecipe.uib.ourrecipes.R;

public class CategoriesDinnerFragment extends Fragment {

    private RecyclerView recyclerView;
    private FoodRecyclerItemAdapter adapter;
    private DatabaseReference recipesRef;
    private ValueEventListener valueEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.d_fragment_categories_dinner, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewDinner);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));

        // Create an empty list for the data
        List<FoodRecipesIconDataClass> data = new ArrayList<>();

        // Create and set the adapter for the RecyclerView
        adapter = new FoodRecyclerItemAdapter(data);
        recyclerView.setAdapter(adapter);

        // Retrieve data from the Firebase Realtimes Database
        recipesRef = FirebaseDatabase.getInstance().getReference().child("Food Recipes").child("Dinner");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data before adding new items
//                data.clear();

                // Iterate through the database snapshots
//                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
//                    String category = categorySnapshot.getKey();
//
//                    for (DataSnapshot recipeSnapshot : categorySnapshot.getChildren()) {

                //the code to just trying to fetch from breakfast
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    // Get the recipe details
                    String id = recipeSnapshot.child("id").getValue(String.class);
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    String rating = recipeSnapshot.child("rating").getValue(String.class);
                    Long times = recipeSnapshot.child("times").getValue(Long.class);
                    String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);

                    // Add additional text to the "times" value
                    String timesText = times + " Minutes"; // Add " minutes" to the times value


                    // Create a Recipe object with the retrieved values
                    FoodRecipesIconDataClass recipe = new FoodRecipesIconDataClass(id, name, rating, times, imageURL);

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