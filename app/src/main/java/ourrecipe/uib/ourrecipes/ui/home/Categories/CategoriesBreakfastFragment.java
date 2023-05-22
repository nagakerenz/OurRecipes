package ourrecipe.uib.ourrecipes.ui.home.Categories;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ourrecipe.uib.ourrecipes.Food.FoodRecipesIconDataClass;
import ourrecipe.uib.ourrecipes.Food.FoodRecyclerItemAdapter;
import ourrecipe.uib.ourrecipes.R;

public class CategoriesBreakfastFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<FoodRecipesIconDataClass> dataList;
    FoodRecyclerItemAdapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food Recipes").child("breakfast");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.d_fragment_categories_breakfast, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBreakfast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataList = new ArrayList<>();
        adapter = new FoodRecyclerItemAdapter(dataList, getActivity());
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dataList.clear(); // Clear the existing data before adding new items

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String id = dataSnapshot.getKey(); // Get the ID of the data
                    FoodRecipesIconDataClass dataClass = dataSnapshot.getValue(FoodRecipesIconDataClass.class);
                    dataClass.setId(id); // Set the ID for the data
                    dataList.add(dataClass);
                }
                Log.d(TAG, "Data: " + snapshot.getValue());
                Toast.makeText(CategoriesBreakfastFragment.this.getActivity(), "Data", Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

        return view;
    }

}