package ourrecipe.uib.ourrecipes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.ui.home.Categories.Categories;
import ourrecipe.uib.ourrecipes.ui.home.Categories.CategoriesBreakfastFragment;

public class ViewPagerImageSliderAdapter extends RecyclerView.Adapter<ViewPagerImageSliderAdapter.ViewHolder> {

    ArrayList<ViewPagerImageSliderDataClass> viewPagerImageSliderDataClassArrayList;

    public ViewPagerImageSliderAdapter(ArrayList<ViewPagerImageSliderDataClass> viewPagerImageSliderDataClassArrayList) {
        this.viewPagerImageSliderDataClassArrayList = viewPagerImageSliderDataClassArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.c_fragment_home_image_slider_viewpager2,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ViewPagerImageSliderDataClass viewPagerImageSliderDataClass = viewPagerImageSliderDataClassArrayList.get(position);

//        holder.imageView.setImageResource(viewPagerImageSliderDataClass.imageID);
//        holder.textView.setImageResource(viewPagerImageSlider.THE WORD);

        Glide.with(holder.itemView.getContext())
                .load(viewPagerImageSliderDataClass.getImageURL())
                .centerCrop()
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Categories.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewPagerImageSliderDataClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageButton);
//            textView = itemView.findViewById(R.id.THE WORD);
            cardView = itemView.findViewById(R.id.cardViewImageSlider);

        }
    }
}
