package ourrecipe.uib.ourrecipes.Food;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.R;

public class FoodRecyclerItemAdapter extends RecyclerView.Adapter<FoodRecyclerItemAdapter.MyViewHolder> {

    private List<FoodRecipesIconDataClass> data;

    public FoodRecyclerItemAdapter(List<FoodRecipesIconDataClass> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.f_activity_food_recipe_icon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FoodRecipesIconDataClass item = data.get(position);
        holder.foodName.setText(item.getTitle());
        holder.foodRating.setText(item.getRating());
        holder.foodtimes.setText(String.valueOf(item.getTimesText())); // Convert Long to String

        // Use Glide library to load the image into the ImageButton
        Glide.with(holder.itemView.getContext())
                .load(item.getimageURL())
                .centerCrop()
                .into(holder.foodImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food Recipes");
                int itemId = position; // Assuming the position in the RecyclerView represents the ID value
                String itemIdString = String.valueOf(itemId);

                DatabaseReference itemReference = databaseReference.child(itemIdString);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Parent node data is available
                            // You can access the parent node key using dataSnapshot.getKey() (e.g., "0", "1", "2")
                            String parentNodeKey = dataSnapshot.getKey();

                            // To retrieve the category ("Breakfast", "Lunch", "Dinner"), you can access the parent node of the current item
                            DatabaseReference parentReference = dataSnapshot.getRef().getParent();

                            ValueEventListener parentValueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot parentDataSnapshot) {
                                    if (parentDataSnapshot.exists()) {
                                        // Parent category node data is available
                                        // You can access the parent category key using parentDataSnapshot.getKey() (e.g., "Breakfast", "Lunch", "Dinner")
                                        String parentCategoryKey = parentDataSnapshot.getKey();

                                        // You can perform any operations with the retrieved parent nodes, such as storing them in variables or ignoring them if not needed for display
                                        // For example:
                                        // String parentNodeKey = dataSnapshot.getKey();
                                        // String parentCategoryKey = parentDataSnapshot.getKey();

                                        // Continue with other operations or set data to the views
                                        // ...

                                        // Retrieve the clicked recipe data
                                        FoodRecipesIconDataClass clickedRecipe = data.get(position);
                                        String recipeId = clickedRecipe.getTitle(); // Or use any other unique identifier from the data class

                                        // Pass the recipeId to FoodRecipes activity
                                        Intent intent = new Intent(holder.itemView.getContext(), FoodRecipes.class);
                                        intent.putExtra("parentKey", parentNodeKey);
                                        intent.putExtra("recipeId", recipeId);
                                        holder.itemView.getContext().startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle any errors that occur during data retrieval
                                }
                            };

                            parentReference.addListenerForSingleValueEvent(parentValueEventListener);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that occur during data retrieval
                    }
                };
                itemReference.addListenerForSingleValueEvent(valueEventListener);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void searchDataList(ArrayList<FoodRecipesIconDataClass> searchList) {
        data = searchList;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView foodImage;
        public TextView foodName;
        public TextView foodRating;
        public TextView foodtimes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.foodRecipeIcon);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodRating = itemView.findViewById(R.id.foodRating);
            foodtimes = itemView.findViewById(R.id.foodtimes);

        }
    }
}
