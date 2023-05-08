package ourrecipe.uib.ourrecipes.ui.home.Categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ourrecipe.uib.ourrecipes.R;

public class FoodIconAdapter  extends BaseAdapter {

    private ArrayList<FoodIconDataClass> dataList;
    private Context context;
    LayoutInflater layoutInflater;

    public FoodIconAdapter(ArrayList<FoodIconDataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_food_recipes_icon, null);
        }
            ImageView gridImage = convertView.findViewById(R.id.gridImage);
            TextView gridLiked = convertView.findViewById(R.id.liked);
            TextView gridFoodName = convertView.findViewById(R.id.foodName);
            TextView gridRating = convertView.findViewById(R.id.rating);
            TextView gridTime = convertView.findViewById(R.id.time);

        Glide.with(context).load(dataList.get(i).getImageIconURL()).into(gridImage);
        gridLiked.setText(dataList.get(i).getLiked());
        gridFoodName.setText(dataList.get(i).getFoodName());
        gridRating.setText(dataList.get(i).getRating());
        gridTime.setText(dataList.get(i).getTime());

        return null;
    }
}
