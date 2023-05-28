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

import ourrecipe.uib.ourrecipes.Food.FoodRecipesIconDataClass;
import ourrecipe.uib.ourrecipes.Food.FoodRecyclerItemAdapter;
import ourrecipe.uib.ourrecipes.R;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.databinding.CFragmentSearchBinding;

public class CSearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private FoodRecyclerItemAdapter adapter;
    private DatabaseReference recipesRef;
    private ValueEventListener valueEventListener;
    private List<FoodRecipesIconDataClass> allData;
    private List<FoodRecipesIconDataClass> filteredData;
    private String currentQuery = ""; // Store the current search query
    private CFragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = CFragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.search1);
        recyclerView = root.findViewById(R.id.recyclerViewSearched);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        allData = new ArrayList<>();
        filteredData = new ArrayList<>();
        adapter = new FoodRecyclerItemAdapter(filteredData);
        recyclerView.setAdapter(adapter);

        recipesRef = FirebaseDatabase.getInstance().getReference().child("Food Recipes");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allData.clear();
                filteredData.clear();

                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot recipeSnapshot : categorySnapshot.getChildren()) {
                        String id = recipeSnapshot.child("id").getValue(String.class);
                        String title = recipeSnapshot.child("title").getValue(String.class);
                        String rating = recipeSnapshot.child("rating").getValue(String.class);
                        Long times = recipeSnapshot.child("times").getValue(Long.class);
                        String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);

                        FoodRecipesIconDataClass recipe = new FoodRecipesIconDataClass(id, title, rating, times, imageURL);
                        allData.add(recipe);
                    }
                }

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
            for (FoodRecipesIconDataClass dataClass : allData) {
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