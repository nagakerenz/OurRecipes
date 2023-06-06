package ourrecipe.uib.ourrecipes.ui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.Food.FoodIconRecipesDataClass;
import ourrecipe.uib.ourrecipes.Food.FoodIconRecyclerItemAdapter;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.CFragmentHomeBinding;
import ourrecipe.uib.ourrecipes.ui.home.Categories.Categories;
import ourrecipe.uib.ourrecipes.ui.reels.VideoDataClass;
import ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter;

public class CHomeFragment extends Fragment {
    private ProgressBar progressBar;

    // ViewPager
    Button reels;
    private ViewPager2 viewPagerImage, viewPagerReels;
    ArrayList<ViewPagerImageSlider> viewPagerImageSliderArrayList;
    private List<VideoDataClass> videoDataClassList;
    private VideoAdapter videoAdapter;
    private VideoAdapterHome videoAdapterHome;

    // Categories
    CardView cardView, breakfast, lunch, dinner, fiber, drink;

    // Recommended Food Recommendation
    private RecyclerView recyclerView, recyclerViewReels;
    private FoodIconRecyclerItemAdapter adapter;
    private DatabaseReference recipesRef;
    private ValueEventListener valueEventListener;
    private boolean isSoundEnabled = false;

    public CHomeFragment(){

    }

    private CFragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View myView = inflater.inflate(R.layout.c_fragment_home, container, false);
        binding = CFragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBar = root.findViewById(R.id.progressBar);
        // Initialize the RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //Categories Image Button
        cardView = root.findViewById(R.id.cardViewImageSlider);
        breakfast = root.findViewById(R.id.breakfast);
        lunch = root.findViewById(R.id.lunch);
        dinner = root.findViewById(R.id.dinner);
        fiber = root.findViewById(R.id.fiber);
        drink = root.findViewById(R.id.drink);
        viewPagerImage = root.findViewById(R.id.viewPager2Image);

        cardView.setCardBackgroundColor(Color.TRANSPARENT);

        //THIS IS FOR HANDLING SLIDER ON FEATURED DISH
        int[] images = { R.drawable.c_home_slide_areyouondiet, R.drawable.c_home_slide_learntocook, R.drawable.c_home_slide_carvingforsteak};
        //TO Implement Strings
//        String[] heading = {"Breakfast, Lunch, Dinner, Dessert, Drinks"};
//        String[] desc = {
//                getString(R.string.YOURSTING)
//        };

        viewPagerImageSliderArrayList = new ArrayList<>();

        for (int i = 0; i < images.length ; i++) {

            ViewPagerImageSlider viewPagerImageSlider = new ViewPagerImageSlider(images[i]); //, heading[i], desc[i]
            viewPagerImageSliderArrayList.add(viewPagerImageSlider);
        }

        ViewPagerImageSliderAdapter viewPagerImageSliderAdapter = new ViewPagerImageSliderAdapter(viewPagerImageSliderArrayList);

        viewPagerImage.setAdapter(viewPagerImageSliderAdapter);
        viewPagerImage.setClipToPadding(false);
        viewPagerImage.setClipChildren(false);
        viewPagerImage.setOffscreenPageLimit(2);
        viewPagerImage.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Categories.class);
                intent.putExtra("categoriesBreakfast", "breakfast");
                startActivity(intent);
            }
        });
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Categories.class);
                intent.putExtra("categoriesLunch", "lunch");
                startActivity(intent);
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Categories.class);
                intent.putExtra("categoriesDinner", "dinner");
                startActivity(intent);
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Categories.class);
                intent.putExtra("categoriesDrink", "drink");
                startActivity(intent);
            }
        });
        fiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Categories.class);
                intent.putExtra("categoriesFiber", "fiber");
                startActivity(intent);
            }
        });

        recyclerViewReels = root.findViewById(R.id.recyclerViewReels);
        recyclerViewReels.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Create and set the adapter for the RecyclerView
        videoAdapterHome = new VideoAdapterHome(new ArrayList<>(), getActivity());
        recyclerViewReels.setAdapter(videoAdapterHome);

        videoAdapterHome.setSoundEnabled(false); // Disable sound
        // Retrieve video data from the Firebase database
        FirebaseDatabase.getInstance().getReference("Food Video")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<VideoDataClass> videoList = new ArrayList<>();

                for (DataSnapshot videoSnapshot : dataSnapshot.getChildren()) {
                    VideoDataClass videoData = videoSnapshot.getValue(VideoDataClass.class);
                    String videoURL = videoSnapshot.child("videoURL").getValue(String.class);
                    videoData.setVideoURL(videoURL);
                    videoList.add(videoData);
                }

                // Update the adapter with the video data
                videoAdapterHome.updateVideoList(videoList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });


        //Food Recommendation Recipes
        // Create an empty list for the data
        List<FoodIconRecipesDataClass> data = new ArrayList<>();

        // Create and set the adapter for the RecyclerView
        adapter = new FoodIconRecyclerItemAdapter(data);
        recyclerView.setAdapter(adapter);

        // Retrieve data from the Firebase Realtimes Database
        recipesRef = FirebaseDatabase.getInstance().getReference().child("Food Recipes").child("Drama");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data before adding new items
                data.clear();
                progressBar.setVisibility(View.VISIBLE);

                // Iterate through the recipe snapshots
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    // Get the recipe details
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    Double rating = recipeSnapshot.child("rating").getValue(Double.class);
                    Long times = recipeSnapshot.child("times").getValue(Long.class);
                    String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);
                    Long liked = recipeSnapshot.child("liked").getValue(Long.class);
                    List<String> likedUser = new ArrayList<>();
                    for (DataSnapshot likedUserSnapshot : recipeSnapshot.child("likedUser").getChildren()) {
                        likedUser.add(likedUserSnapshot.getValue(String.class));
                    }
                    // Retrieve the current user's ID
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = currentUser.getUid();
                    // Check if the current user's ID exists in the likedUser list
                    boolean isLiked = likedUser.contains(userId);

                    // Retrieve the parentKey and parentCategoryKey from the snapshot's reference
                    String parentKey = recipeSnapshot.getRef().getParent().getKey();
                    String parentCategoryKey = recipeSnapshot.getRef().getParent().getParent().getKey();

                    // Add additional text to the "times" value
                    String timesText = times + " Minutes"; // Add " minutes" to the times value

                    // Create a Recipe object with the retrieved values
                    FoodIconRecipesDataClass recipe = new FoodIconRecipesDataClass(name, rating, times, imageURL, parentKey, parentCategoryKey, liked, likedUser, isLiked);

                    // Add the recipe to the list
                    data.add(recipe);
                }

                // Notify the adapter about the data change
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        };
        recipesRef.addValueEventListener(valueEventListener);

        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the ValueEventListener when the fragment is destroyed
        if (recipesRef != null && valueEventListener != null) {
            recipesRef.removeEventListener(valueEventListener);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}