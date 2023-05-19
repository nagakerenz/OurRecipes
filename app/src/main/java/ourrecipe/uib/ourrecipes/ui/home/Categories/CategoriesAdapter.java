package ourrecipe.uib.ourrecipes.ui.home.Categories;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoriesAdapter extends FragmentStateAdapter {

    public CategoriesAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new CategoriesBreakfastFragment();
            case 1:
                return new CategoriesLunchFragment();
            case 2:
                return new CategoriesDinnerFragment();
            case 3:
                return new CategoriesDrinkFragment();
            default:
                return new CategoriesFiberFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
