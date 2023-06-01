package ourrecipe.uib.ourrecipes.Food;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class FoodIconRecyclerItemAdapter extends RecyclerView.Adapter<FoodIconRecyclerItemAdapter.MyViewHolder> {

    private static List<FoodIconRecipesDataClass> data;


    public FoodIconRecyclerItemAdapter(List<FoodIconRecipesDataClass> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.f_activity_food_recipes_icon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int itemPosition = position; // Create a final variable and assign the value of position

        FoodIconRecipesDataClass item = data.get(position);
        holder.foodName.setText(item.getTitle());
        holder.foodRating.setText(item.getRating());
        holder.foodTimes.setText(String.valueOf(item.getTimesText())); // Convert Long to String

        // Use Glide library to load the image into the ImageButton
        Glide.with(holder.itemView.getContext())
                .load(item.getImageURL())
                .centerCrop()
                .into(holder.foodImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food Recipes");

                DatabaseReference itemReference = databaseReference.child(item.getParentKey()).child(String.valueOf(itemPosition));
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String parentKey = item.getParentKey();
                            String childKey = String.valueOf(itemPosition);

                            Intent intent = new Intent(holder.itemView.getContext(), FoodRecipes.class);
                            intent.putExtra("parentKey", parentKey);
                            intent.putExtra("childKey", childKey);

                            holder.itemView.getContext().startActivity(intent);
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
    public void searchDataList(ArrayList<FoodIconRecipesDataClass> searchList) {
        data = searchList;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView foodImage;
        public TextView foodName;
        public TextView foodRating;
        public TextView foodTimes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.foodRecipeIcon);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodRating = itemView.findViewById(R.id.foodRating);
            foodTimes = itemView.findViewById(R.id.foodTimes);


        }
    }
}
