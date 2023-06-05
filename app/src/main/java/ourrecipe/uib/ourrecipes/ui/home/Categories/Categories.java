package ourrecipe.uib.ourrecipes.ui.home.Categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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
        tabLayout = findViewById(R.id.tabLayout);

        // Retrieve selected fragment from the intent
        String categoriesBreakfastFragment = getIntent().getStringExtra("categoriesBreakfast");
        String categoriesLunchFragment = getIntent().getStringExtra("categoriesLunch");
        String categoriesDinnerFragment = getIntent().getStringExtra("categoriesDinner");
        String categoriesDrinkFragment = getIntent().getStringExtra("categoriesDrink");
        String categoriesFiberFragment = getIntent().getStringExtra("categoriesFiber");

        viewPager2.setAdapter(new CategoriesAdapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Breakfast");
                        tab.setIcon(R.drawable.d_food_categories_breakfast);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.orange));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 1: {
                        tab.setText("Lunch");
                        tab.setIcon(getResources().getDrawable(R.drawable.d_food_categories_lunch)); // Set the icon resource directly
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.orange));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 2: {
                        tab.setText("Dinner");
                        tab.setIcon(getResources().getDrawable(R.drawable.d_food_categories_dinner)); // Set the icon resource directly
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.orange));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 3: {
                        tab.setText("Drink");
                        tab.setIcon(R.drawable.d_food_categories_drink); // Set the icon resource directly
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.orange));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                    case 4: {
                        tab.setText("Fiber");
                        tab.setIcon(R.drawable.d_food_categories_fiber); // Set the icon resource directly
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.orange));
                        badgeDrawable.setVisible(true);
                        break;
                    }
                }

                tab.removeBadge(); // Remove the badge for each tab

            }
        }); tabLayoutMediator.attach();

        // Set the current item based on the selected fragment
        if (categoriesBreakfastFragment != null && categoriesBreakfastFragment.equals("breakfast")) {
            viewPager2.setCurrentItem(0);
        } else if (categoriesLunchFragment != null && categoriesLunchFragment.equals("lunch")) {
            viewPager2.setCurrentItem(1);
        } else if (categoriesDinnerFragment != null && categoriesDinnerFragment.equals("dinner")) {
            viewPager2.setCurrentItem(2);
        } else if (categoriesDrinkFragment != null && categoriesDrinkFragment.equals("drink")) {
            viewPager2.setCurrentItem(3);
        } else if (categoriesFiberFragment != null && categoriesFiberFragment.equals("fiber")) {
            viewPager2.setCurrentItem(4);
        }

//        to fit a lot of categories enable this to make it scrollable
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

    }
}