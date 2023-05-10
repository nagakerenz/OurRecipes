package ourrecipe.uib.ourrecipes.ui.home.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.R;

public class Categories extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private ImageButton breakfastButton, lunchButton, dinnerButton, drinkButton, fiberButton;
    private ViewPagerFoodCategoriesAdapter pagerAdapter;
    private List<String> categoryTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        viewPager2 = findViewById(R.id.viewPager2FoodCategories);
        tabLayout = findViewById(R.id.tabLayoutFoodCategories);
        // Get references to the category ImageButtons
        breakfastButton = findViewById(R.id.breakfast);
        lunchButton = findViewById(R.id.lunch);
        dinnerButton = findViewById(R.id.dinner);
        drinkButton = findViewById(R.id.drink);
        fiberButton = findViewById(R.id.fiber);

        // Initialize category titles
        categoryTitles = new ArrayList<>();
        categoryTitles.add("Breakfast");
        categoryTitles.add("Lunch");
        categoryTitles.add("Dinner");
        categoryTitles.add("Drink");
        categoryTitles.add("Fiber");

        // Create an adapter for the ViewPager2
        pagerAdapter = new ViewPagerFoodCategoriesAdapter(categoryTitles, new ArrayList<>(), tabLayout);
        viewPager2.setAdapter(pagerAdapter);

        // Connect the TabLayout with the ViewPager2
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(pagerAdapter.getPageTitle(position))
        ).attach();

        // Set up click listeners for the category ImageButtons
        breakfastButton.setOnClickListener(v -> setCurrentCategory(0));
        lunchButton.setOnClickListener(v -> setCurrentCategory(1));
        dinnerButton.setOnClickListener(v -> setCurrentCategory(2));
        drinkButton.setOnClickListener(v -> setCurrentCategory(3));
        fiberButton.setOnClickListener(v -> setCurrentCategory(4));

        // Set the initial selected category (e.g., breakfast)
        setCurrentCategory(0);
    }

    private void setCurrentCategory(int categoryIndex) {
        if (categoryIndex >= 0 && categoryIndex < categoryTitles.size()) {
            viewPager2.setCurrentItem(categoryIndex);
            updateHighlightedCategory(categoryIndex);
        }
    }

    private void updateHighlightedCategory(int categoryIndex) {
        // Reset the highlight for all categories
        resetHighlight();

        // Set the highlight for the selected category
        switch (categoryIndex) {
            case 0:
                breakfastButton.setBackgroundColor(getResources().getColor(R.color.highlightColor));
                break;
            case 1:
                lunchButton.setBackgroundColor(getResources().getColor(R.color.highlightColor));
                break;
            case 2:
                dinnerButton.setBackgroundColor(getResources().getColor(R.color.highlightColor));
                break;
            case 3:
                drinkButton.setBackgroundColor(getResources().getColor(R.color.highlightColor));
                break;
            case 4:
                fiberButton.setBackgroundColor(getResources().getColor(R.color.highlightColor));
                break;
        }
    }

    private void resetHighlight() {
        breakfastButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        lunchButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        dinnerButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        drinkButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        fiberButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
}
