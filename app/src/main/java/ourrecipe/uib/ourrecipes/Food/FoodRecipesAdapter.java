//package ourrecipe.uib.ourrecipes;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//public class FoodRecipesAdapter extends RecyclerView.Adapter<FoodRecipesAdapter.RecipeViewHolder> {
//    private List<Recipe> recipes; // List of recipe data
//
//    public FoodRecipesAdapter(List<Recipe> recipes) {
//        this.recipes = recipes;
//    }
//
//    // Create the view holder for each recipe item
//    @Override
//    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
//        return new RecipeViewHolder(view);
//    }
//
//    // Bind the recipe data to the view holder
//    @Override
//    public void onBindViewHolder(RecipeViewHolder holder, int position) {
//        Recipe recipe = recipes.get(position);
//        // Set the recipe data to the views in the view holder
//        holder.bind(recipe);
//    }
//
//    // Return the total number of recipes
//    @Override
//    public int getItemCount() {
//        return recipes.size();
//    }
//
//    // View holder class to hold the views for each recipe item
//    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
//        private ImageButton imageButton;
//        private TextView nameTextView;
//        private TextView ratingTextView;
//        private TextView timeTextView;
//        private ImageButton loveButton;
//
//        public RecipeViewHolder(View itemView) {
//            super(itemView);
//            imageButton = itemView.findViewById(R.id.imageButton);
//            nameTextView = itemView.findViewById(R.id.nameTextView);
//            ratingTextView = itemView.findViewById(R.id.ratingTextView);
//            timeTextView = itemView.findViewById(R.id.timeTextView);
//            loveButton = itemView.findViewById(R.id.loveButton);
//        }
//
//        // Bind the recipe data to the views
//        public void bind(Recipe recipe) {
//            // Set the recipe data to the views
//            imageButton.setImageResource(recipe.getImageResource());
//            nameTextView.setText(recipe.getName());
//            ratingTextView.setText(recipe.getRating());
//            timeTextView.setText(recipe.getTime());
//            // Set click listener for the love button
//            loveButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle the love button click
//                    // Save the recipe or perform any desired action
//                }
//            });
//        }
//    }
//}
