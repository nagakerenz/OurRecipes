package ourrecipe.uib.ourrecipes.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ourrecipe.uib.ourrecipes.R;

public class ViewPagerImageSliderAdapter extends RecyclerView.Adapter<ViewPagerImageSliderAdapter.ViewHolder> {

    ArrayList<ViewPagerImageSlider> viewPagerImageSliderArrayList;

    public ViewPagerImageSliderAdapter(ArrayList<ViewPagerImageSlider> viewPagerImageSliderArrayList) {
        this.viewPagerImageSliderArrayList = viewPagerImageSliderArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_image_slider_viewpager2,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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
