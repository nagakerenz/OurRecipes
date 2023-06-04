package ourrecipe.uib.ourrecipes.Food.viewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.IngredientViewHolder> {

    private List<IngredientFragmentDataClass> ingredientsList;

    public IngredientRecyclerViewAdapter(List<IngredientFragmentDataClass> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.f_activity_food_recipes_fragment_ingredients_icon, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientFragmentDataClass ingredient = ingredientsList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private ImageView ingredientImageView;
        private TextView ingredientNameTextView;
        private TextView ingredientPortionTextView;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImageView = itemView.findViewById(R.id.foodRecipesIngredientsImage);
            ingredientNameTextView = itemView.findViewById(R.id.foodRecipesIngredientsName);
            ingredientPortionTextView = itemView.findViewById(R.id.foodRecipesIngredientsPortion);
        }

        public void bind(IngredientFragmentDataClass ingredient) {
            ingredientNameTextView.setText(ingredient.getName());
            ingredientPortionTextView.setText(ingredient.getPortion());

            Glide.with(itemView.getContext())
                    .load(ingredient.getImage())
                    .centerCrop()
                    .into(ingredientImageView);
        }
    }
}
