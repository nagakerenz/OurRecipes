package ourrecipe.uib.ourrecipes.ui.home.Categories;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ourrecipe.uib.ourrecipes.ui.home.Categories.breakfast.BreakfastFragment;
import ourrecipe.uib.ourrecipes.ui.home.Categories.dinner.DinnerFragment;
import ourrecipe.uib.ourrecipes.ui.home.Categories.lunch.LunchFragment;

public class ViewPagerCategoriesNavigationAdapter extends FragmentStateAdapter {

    public ViewPagerCategoriesNavigationAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BreakfastFragment();
            case 1:
                return new LunchFragment();
            default:
                return new DinnerFragment();
        }
    }
}
