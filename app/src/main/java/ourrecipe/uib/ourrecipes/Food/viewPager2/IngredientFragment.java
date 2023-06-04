package ourrecipe.uib.ourrecipes.Food.viewPager2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;


public class IngredientFragment extends Fragment {

    private List<IngredientFragmentDataClass> ingredientsList;

    public IngredientFragment(List<IngredientFragmentDataClass> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public IngredientFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.f_activity_food_recipes_fragment_ingredient, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewIngredients);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        // Initialize and set up the RecyclerView adapter
        IngredientRecyclerViewAdapter adapter = new IngredientRecyclerViewAdapter(ingredientsList);
        recyclerView.setAdapter(adapter);

        Toast.makeText(requireContext(), "Data retrieved: " + ingredientsList.size() + " ingredients", Toast.LENGTH_SHORT).show();

        return view;

    }
}