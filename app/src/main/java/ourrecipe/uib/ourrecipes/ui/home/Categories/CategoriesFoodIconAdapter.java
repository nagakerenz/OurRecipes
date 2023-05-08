package ourrecipe.uib.ourrecipes.ui.home.Categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.ui.home.ViewPagerImageSlider;

public class CategoriesFoodIconAdapter extends RecyclerView.Adapter<ourrecipe.uib.ourrecipes.ui.home.CategoriesFoodIconAdapter.ViewHolder> {

    ArrayList<CategoriesFoodIcon> categoriesFoodIconArrayList;

    public CategoriesFoodIconAdapter(ArrayList<CategoriesFoodIcon> categoriesFoodIconArrayList) {
        this.categoriesFoodIconArrayList = categoriesFoodIconArrayList;
    }

    @NonNull
    @Override
    public ourrecipe.uib.ourrecipes.ui.home.ViewPagerImageSliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_image_slider_viewpager2,parent,false);

        return new ourrecipe.uib.ourrecipes.ui.home.ViewPagerImageSliderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ourrecipe.uib.ourrecipes.ui.home.ViewPagerImageSliderAdapter.ViewHolder holder, int position) {

        ViewPagerImageSlider viewPagerImageSlider = viewPagerImageSliderArrayList.get(position);

        holder.imageView.setImageResource(viewPagerImageSlider.imageID);
//        holder.textView.setImageResource(viewPagerImageSlider.THE WORD);
    }

    @Override
    public int getItemCount() {
        return viewPagerImageSliderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageButton);
//            textView = itemView.findViewById(R.id.THE WORD);
        }
    }
}
