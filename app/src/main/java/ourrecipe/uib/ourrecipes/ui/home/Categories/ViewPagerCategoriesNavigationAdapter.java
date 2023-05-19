package ourrecipe.uib.ourrecipes.ui.home.Categories;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
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
                return new CategoriesBreakfastFragment();
            case 1:
                return new CategoriesLunchFragment();
            default:
                return new CategoriesDinnerFragment();
        }
    }
}
