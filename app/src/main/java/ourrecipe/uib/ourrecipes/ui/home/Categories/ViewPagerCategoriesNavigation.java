package ourrecipe.uib.ourrecipes.ui.home.Categories;

import java.util.List;

public class ViewPagerCategoriesNavigation {
    private String title;
    private List<String> items;

    public ViewPagerCategoriesNavigation(String title, List<String> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getItems() {
        return items;
    }
}

