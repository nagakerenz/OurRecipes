package ourrecipe.uib.ourrecipes.Food.viewPager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class IngredientAndStepAdapter extends FragmentStateAdapter {


    public IngredientAndStepAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new IngredientFragment();
        }
        return new StepFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
