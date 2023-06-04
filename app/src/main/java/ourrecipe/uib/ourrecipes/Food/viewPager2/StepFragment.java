package ourrecipe.uib.ourrecipes.Food.viewPager2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class StepFragment extends Fragment {

    private TextView stepsTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.f_activity_food_recipes_fragment_step, container, false);

        stepsTextView = view.findViewById(R.id.foodSteps);

        // Retrieve the steps from the arguments
        ArrayList<String> steps = getArguments().getStringArrayList("steps");
        if (steps != null) {
            Toast.makeText(getActivity(), "Number of steps: " + steps.size(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Steps: " + steps.toString(), Toast.LENGTH_LONG).show();

//            StringBuilder stepsStringBuilder = new StringBuilder();
//            for (int i = 0; i < steps.size(); i++) {
//                String step = steps.get(i);
//                int stepNumber = i + 1;
//                stepsStringBuilder.append(stepNumber).append(". ").append(step).append("\n");
//            }
//            String stepsText = stepsStringBuilder.toString();
            stepsTextView.setText(steps.toString());
        } else {
            Toast.makeText(getActivity(), "Steps list is null", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

}