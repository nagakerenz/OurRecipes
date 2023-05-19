package ourrecipe.uib.ourrecipes.ui.home.Categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentCategoriesNavigationBinding;

public class CategoriesNavigationFragment extends Fragment {

    private FragmentCategoriesNavigationBinding binding;
    private Map<String, Integer> tabTitles = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriesNavigationBinding.inflate(inflater);
        setUpTabLayoutWithViewPager();
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabTitles.put("Breakfast", R.drawable.d_categories_breakfast);
        tabTitles.put("Lunch", R.drawable.d_categories_lunch);
        tabTitles.put("Dinner", R.drawable.d_categories_dinner);
    }

    private void setUpTabLayoutWithViewPager() {
        List<String> titles = new ArrayList<>(tabTitles.keySet());
        binding.viewPagerCategoriesFood.setAdapter(new ViewPagerCategoriesNavigationAdapter(this));
        new TabLayoutMediator(binding.tabLayout, binding.viewPagerCategoriesFood, (tab, position) -> {
            tab.setText(titles.get(position));
        }).attach();

        for (int index = 0; index < tabTitles.size(); index++) {
            int imageResId = tabTitles.get(titles.get(index));
            TextView textView = (TextView) LayoutInflater.from(requireContext())
                    .inflate(R.layout.activity_categories_navigation_tab_title, null);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, imageResId, 0, 0);
            float density = getResources().getDisplayMetrics().density;
            int padding = (int) (4f * density);
            textView.setCompoundDrawablePadding(padding);
            binding.tabLayout.getTabAt(index).setCustomView(textView);
        }
    }
}
