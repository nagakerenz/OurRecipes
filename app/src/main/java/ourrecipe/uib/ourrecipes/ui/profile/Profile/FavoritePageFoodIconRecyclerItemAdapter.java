package ourrecipe.uib.ourrecipes.ui.profile.Profile;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodIconRecipesDataClass;
import ourrecipe.uib.ourrecipes.Food.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;

public class FavoritePageFoodIconRecyclerItemAdapter extends RecyclerView.Adapter<FavoritePageFoodIconRecyclerItemAdapter.MyViewHolder> {

    private List<FoodIconRecipesDataClass> data;

    public FavoritePageFoodIconRecyclerItemAdapter(List<FoodIconRecipesDataClass> data) {
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
        final int itemPosition = position;
        final FoodIconRecipesDataClass item = data.get(itemPosition);

        holder.foodName.setText(item.getTitle());
        holder.foodRating.setText(String.valueOf(item.getRating()));
        holder.foodTimes.setText(String.valueOf(item.getTimesText()));
        holder.foodLiked.setText(String.valueOf(item.getLiked()));

        Glide.with(holder.itemView.getContext())
                .load(item.getImageURL())
                .centerCrop()
                .into(holder.foodImage);

        // Check if the current user's ID exists in the likedUser list
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        boolean isLiked = item.isLiked();

        // Update the state and image resource of the likeButton
        if (isLiked) {
            holder.likeButton.setSelected(true);
            holder.likeButton.setImageResource(R.drawable.f_food_recipe_icon_favorite_button_red);
        } else {
            holder.likeButton.setSelected(false);
            holder.likeButton.setImageResource(R.drawable.f_food_recipe_icon_favorite_button_white);
        }
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();
                String provider = "";
                for (UserInfo userInfo : currentUser.getProviderData()) {
                    if (userInfo.getProviderId().equals("facebook.com")) {
                        provider = "FacebookUser";
                        break;
                    } else if (userInfo.getProviderId().equals("google.com")) {
                        provider = "GoogleUser";
                        break;
                    }
                }
                if (provider.isEmpty()) {
                    provider = "User";
                }

                boolean isChecked = view.isSelected();
                view.setSelected(!isChecked);

                int likeButtonImageResource = view.isSelected() ? R.drawable.f_food_recipe_icon_favorite_button_red : R.drawable.f_food_recipe_icon_favorite_button_white;
                holder.likeButton.setImageResource(likeButtonImageResource);

                DatabaseReference databaseReferenceId = FirebaseDatabase.getInstance().getReference("Food Recipes")
                        .child(item.getParentKey())
                        .child(String.valueOf(itemPosition))
                        .child("ID");

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food Recipes")
                        .child(item.getParentKey())
                        .child(String.valueOf(itemPosition))
                        .child("liked");

                DatabaseReference likedUserReference = FirebaseDatabase.getInstance().getReference("Food Recipes")
                        .child(item.getParentKey())
                        .child(String.valueOf(itemPosition))
                        .child("likedUser");

                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User Profile")
                        .child(provider)
                        .child(userId)
                        .child("favoriteRecipes");


                if (view.isSelected()) {
                    long newLiked = item.getLiked() + 1;
                    item.setLiked(newLiked);
                    databaseReference.setValue(newLiked);

                    // Generate a new unique ID for likedUser starting from 0
                    // Retrieve the "ID" value from "Food Recipes"
                    databaseReferenceId.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String foodId = dataSnapshot.getValue(String.class);
                            if (foodId != null) {
                                // Store the "ID" value under the user's favoriteRecipes
                                DatabaseReference newFavoriteRef = userReference.child(foodId);
                                newFavoriteRef.child("Category").setValue(item.getParentKey());
                                newFavoriteRef.child("ID").setValue(String.valueOf(itemPosition));
                                newFavoriteRef.child("FoodID").setValue(foodId);
                            }
                        }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });

                        likedUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                long likedUserCount = snapshot.getChildrenCount();
                                likedUserReference.child(String.valueOf(likedUserCount)).setValue(userId);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle error
                            }
                        });

                } else {
                    long newLiked = item.getLiked() - 1;
                    item.setLiked(newLiked);
                    databaseReference.setValue(newLiked);

                    // Find the favorite recipe with the specific category and ID, and remove it
                    userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                String category = childSnapshot.child("Category").getValue(String.class);
                                String foodId = childSnapshot.child("ID").getValue(String.class);

                                if (category != null && category.equals(item.getParentKey()) && foodId != null && foodId.equals(String.valueOf(itemPosition))) {
                                    childSnapshot.getRef().removeValue(); // Remove the favorite recipe
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });

                    // Remove the likedUser entry with the specific userID
                    likedUserReference.orderByValue().equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                childSnapshot.getRef().removeValue();
                                break;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }
            }
        });

        DatabaseReference itemReference = FirebaseDatabase.getInstance().getReference("Food Recipes")
                .child(item.getParentKey())
                .child(String.valueOf(itemPosition));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void searchDataList(List<FoodIconRecipesDataClass> searchList) {
        data = searchList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView foodImage;
        public TextView foodName, foodRating, foodTimes, foodLiked;
        public ImageButton likeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.foodRecipeIcon);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodRating = itemView.findViewById(R.id.foodRating);
            foodTimes = itemView.findViewById(R.id.foodTimes);
            foodLiked = itemView.findViewById(R.id.foodLiked);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }
}