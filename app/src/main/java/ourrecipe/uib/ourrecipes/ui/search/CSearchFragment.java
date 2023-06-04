package ourrecipe.uib.ourrecipes.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.databinding.CFragmentSearchBinding;

public class CSearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView, recyclerViewMostFavoriteDish;
    private FoodIconRecyclerItemAdapter adapter;
    private DatabaseReference recipesRef;
    private ValueEventListener valueEventListener;
    private List<FoodIconRecipesDataClass> allData;
    private List<FoodIconRecipesDataClass> filteredData;
    private String currentQuery = ""; // Store the current search query
    private CFragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = CFragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.search1);
        recyclerView = root.findViewById(R.id.recyclerViewSearched);
        recyclerViewMostFavoriteDish = root.findViewById(R.id.recyclerViewMostFavoriteDish);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        allData = new ArrayList<>();
        filteredData = new ArrayList<>();
        adapter = new FoodIconRecyclerItemAdapter(filteredData);
        recyclerView.setAdapter(adapter);

        recipesRef = FirebaseDatabase.getInstance().getReference().child("Food Recipes");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data before adding new items
                allData.clear();
                filteredData.clear();

                // Iterate through the recipe snapshots
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot recipeSnapshot : categorySnapshot.getChildren()) {
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
                        FoodIconRecipesDataClass recipe = new FoodIconRecipesDataClass(name, rating, times, imageURL, parentKey, parentCategoryKey, liked, isFavorite);

                        // Add the recipe to the list
                        allData.add(recipe);
                    }
                }

                // Notify the adapter about the data change
                filterData(currentQuery); // Apply the filter again when data is updated
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any
            }
        };
        recipesRef.addValueEventListener(valueEventListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText; // Update the current query
                filterData(newText);
                return true;
            }
        });

        return root;
    }

    private void filterData(String query) {
        filteredData.clear();

        if (query.isEmpty()) {
            filteredData.addAll(allData);
        } else {
            for (FoodIconRecipesDataClass dataClass : allData) {
                if (dataClass.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredData.add(dataClass);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recipesRef != null && valueEventListener != null) {
            recipesRef.removeEventListener(valueEventListener);
        }
    }
}
