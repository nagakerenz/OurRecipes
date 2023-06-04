package ourrecipe.uib.ourrecipes.Food.viewPager2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class StepFragment extends Fragment {
    private List<String> steps;
    private TextView stepsTextView;

    public StepFragment(List<String> steps) {
        this.steps = steps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.f_activity_food_recipes_fragment_step, container, false);

        stepsTextView = view.findViewById(R.id.foodSteps);

        return view;
    }

}