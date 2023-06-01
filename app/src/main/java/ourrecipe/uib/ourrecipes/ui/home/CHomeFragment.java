package ourrecipe.uib.ourrecipes.ui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
import ourrecipe.uib.ourrecipes.ui.reels.Video;
import ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter;

public class CHomeFragment extends Fragment {



    //ViewPager
    Button reels;
    private ViewPager2 viewPagerImage, viewPagerReels;
    ArrayList<ViewPagerImageSlider> viewPagerImageSliderArrayList;
    private List<Video> videoList;
    private VideoAdapter videoAdapter;

    //Categories
    CardView cardView;
    ImageButton breakfast;
    ImageButton lunch;
    ImageButton dinner;
    ImageButton fiber;
    ImageButton drink;

    //Recommended Food Recommendation
    private RecyclerView recyclerView;
    private FoodIconRecyclerItemAdapter adapter;
    private DatabaseReference recipesRef;
    private ValueEventListener valueEventListener;

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


        // Initialize the RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //Categories Image Button
        cardView = root.findViewById(R.id.cardViewImageSlider);
        breakfast = (ImageButton) root.findViewById(R.id.breakfast);
        lunch = (ImageButton) root.findViewById(R.id.lunch);
        dinner = (ImageButton) root.findViewById(R.id.dinner);
        fiber = (ImageButton) root.findViewById(R.id.fiber);
        drink = (ImageButton) root.findViewById(R.id.drink);
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

//        reels = (Button) root.findViewById(R.id.reels);
//        reels.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                FragmentTransaction fragmentTransaction = getView().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.reelsFrame, new ReelsFragment());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                getActivity().getFragmentManager().beginTransaction().replace(R.id.container, HomeFragment()).commit();

//                FragmentTransaction fragmentTransaction = .beginTransaction();
//                fragmentTransaction.replace(R.id.container, new ReelsFragment());
//                fragmentTransaction.commit();
//
//                Fragment fragment = new ReelsFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.reels, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
//                Fragment homeFragment = new ReelsFragment();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, homeFragment);
//                transaction.commit();
//            }
//        });

//        return binding.getRoot();
//        binding.reels.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HomeFragment homeFragment = new HomeFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.,homeFragment);
//                transaction.commit();
//            }
//        });
//        reels = myView.findViewById(R.id.goReels);
//        reels.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ReelsFragment reelsFragment = new ReelsFragment();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, reelsFragment).commit();
//            }
//        });

        videoList = new ArrayList<>();
        viewPagerReels = root.findViewById(R.id.viewPagerReels);

        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "This Looks Delicious."));

        videoAdapter = new VideoAdapter(videoList);
        viewPagerReels.setAdapter(videoAdapter);

        videoList = new ArrayList<>();
        viewPagerReels = root.findViewById(R.id.viewPagerReels1);

        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.octo, "Eating", "Seafood is the best."));

        videoAdapter = new VideoAdapter(videoList);
        viewPagerReels.setAdapter(videoAdapter);

        CardView reelsCardView = root.findViewById(R.id.reelsCardView);
        reelsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_HomeFragment_to_ReelsFragment);
            }
        });



        //Food Recomendation Recipes
        // Create an empty list for the data
        List<FoodIconRecipesDataClass> data = new ArrayList<>();

        // Create and set the adapter for the RecyclerView
        adapter = new FoodIconRecyclerItemAdapter(data);
        recyclerView.setAdapter(adapter);

        // Retrieve data from the Firebase Realtimes Database
        recipesRef = FirebaseDatabase.getInstance().getReference().child("Food Recipes").child("Breakfast");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing data before adding new items
                data.clear();

                // Iterate through the recipe snapshots
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    // Get the recipe details
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    String rating = recipeSnapshot.child("rating").getValue(String.class);
                    Long times = recipeSnapshot.child("times").getValue(Long.class);
                    String imageURL = recipeSnapshot.child("imageURL").getValue(String.class);

                    // Retrieve the parentKey and parentCategoryKey from the snapshot's reference
                    String parentKey = recipeSnapshot.getRef().getParent().getKey();
                    String parentCategoryKey = recipeSnapshot.getRef().getParent().getParent().getKey();

                    // Add additional text to the "times" value
                    String timesText = times + " Minutes"; // Add " minutes" to the times value

                    // Create a Recipe object with the retrieved values
                    FoodIconRecipesDataClass recipe = new FoodIconRecipesDataClass(name, rating, times, imageURL, parentKey, parentCategoryKey);

                    // Add the recipe to the list
                    data.add(recipe);
                }

                // Notify the adapter about the data change
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any
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