package ourrecipe.uib.ourrecipes.ui.home.Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ourrecipe.uib.ourrecipes.R;

public class Categories extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_categories);

        getSupportActionBar().hide();

        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.viewPager);

        viewPager2.setAdapter(new CategoriesAdapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Breakfast");
                        tab.setIcon(getResources().getDrawable(R.drawable.d_categories_breakfast));
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 1: {
                        tab.setText("Lunch");
                        tab.setIcon(getResources().getDrawable(R.drawable.d_categories_lunch));
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 2: {
                        tab.setText("Dinner");
                        tab.setIcon(getResources().getDrawable(R.drawable.d_categories_dinner));
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 3: {
                        tab.setText("Drink");
                        tab.setIcon(getResources().getDrawable(R.drawable.food_categories_drink));
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 4: {
                        tab.setText("Fiber");
                        tab.setIcon(getResources().getDrawable(R.drawable.food_categories_fiber));
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                }
            }
        }); tabLayoutMediator.attach();
    }
}