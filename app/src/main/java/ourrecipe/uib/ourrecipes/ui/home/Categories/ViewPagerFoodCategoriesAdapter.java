package ourrecipe.uib.ourrecipes.ui.home.Categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.R;

public class ViewPagerFoodCategoriesAdapter extends RecyclerView.Adapter<ViewPagerFoodCategoriesAdapter.ViewHolder> {

    private List<String> categoryTitles;
    ArrayList<ViewPagerFoodCategories> categoriesList;
    TabLayout tabLayout;

    public ViewPagerFoodCategoriesAdapter(List<String> categoryTitles, ArrayList<ViewPagerFoodCategories> categoriesList, TabLayout tabLayout) {
        this.categoryTitles = categoryTitles;
        this.categoriesList = categoriesList;
        this.tabLayout = tabLayout;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_categories_food_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerFoodCategories categories = categoriesList.get(position);
        holder.textView.setText(categories.getTitle());

        GridViewItemAdapter gridViewItemAdapter = new GridViewItemAdapter(categories.getItems());
        holder.gridView.setAdapter(gridViewItemAdapter);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Get the category title for the given position
        return categoryTitles.get(position);
    }


    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        GridView gridView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.titleFoodCategories);
            gridView = itemView.findViewById(R.id.gridViewFoodCategories);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    setCurrentCategory(position);
                }
            });
        }
    }

    private void setCurrentCategory(int categoryIndex) {
        tabLayout.selectTab(tabLayout.getTabAt(categoryIndex));
    }

    private class GridViewItemAdapter extends BaseAdapter {
        List<String> items;

        public GridViewItemAdapter(List<String> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_food_recipes_icon, parent, false);
            }

            TextView itemTextView = convertView.findViewById(R.id.liked);
            String item = getItem(position);
            itemTextView.setText(item);

            return convertView;
        }
    }
}
