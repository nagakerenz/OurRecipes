package ourrecipe.uib.ourrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ourrecipe.uib.ourrecipes.ui.home.Categories.CategoriesBreakfastFragment;

public class FoodRecyclerItemAdapter extends RecyclerView.Adapter<FoodRecyclerItemAdapter.MyViewHolder> {

    private ArrayList<FoodRecipesIconDataClass> dataList;
    private Context context;

    public FoodRecyclerItemAdapter(ArrayList<FoodRecipesIconDataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f_activity_food_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getFoodImage()).into(holder.foodImage);
        holder.foodName.setText(dataList.get(position).getFoodName());
        holder.foodRating.setText(dataList.get(position).getFoodRating());
        holder.foodTime.setText(dataList.get(position).getFoodTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageButton foodImage;
        TextView foodName, foodRating, foodTime, foodLiked;
//        boolean foodFavorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodRating = itemView.findViewById(R.id.foodRating);
            foodTime = itemView.findViewById(R.id.foodTime);
//            foodLiked = itemView.findViewById(R.id.foodLiked);
//            foodFavorite = itemView.findViewById(R.id.foodFavorite);

        }
    }
}
