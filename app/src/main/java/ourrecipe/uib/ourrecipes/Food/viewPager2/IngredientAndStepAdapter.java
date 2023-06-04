package ourrecipe.uib.ourrecipes.Food.viewPager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class IngredientAndStepAdapter extends FragmentStateAdapter {
    private List<IngredientFragmentDataClass> ingredientsList;
    private List<String> steps;

    public IngredientAndStepAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<IngredientFragmentDataClass> ingredientsList, List<String> steps) {
        super(fragmentManager, lifecycle);
        this.ingredientsList = ingredientsList;
        this.steps = steps;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new IngredientFragment(ingredientsList);
        } else {
            return new StepFragment(steps);

        }
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
