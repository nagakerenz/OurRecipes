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
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.CFragmentHomeBinding;
import ourrecipe.uib.ourrecipes.ui.home.Categories.Categories;
import ourrecipe.uib.ourrecipes.ui.reels.Video;
import ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter;

public class CHomeFragment extends Fragment {
//    MaterialButton reels;
    CardView cardView;
    ImageButton breakfast;
    ImageButton lunch;
    ImageButton dinner;
    ImageButton fiber;
    ImageButton drink;
    ImageButton menu;
    ImageButton menu1;
    ImageButton menu2;
    ImageButton menu3;
    Button reels;
    private ViewPager2 viewPagerImage, viewPagerReels;
    ArrayList<ViewPagerImageSlider> viewPagerImageSliderArrayList;
    private List<Video> videoList;
    private VideoAdapter adapter;
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

        cardView = root.findViewById(R.id.cardViewImageSlider);
        breakfast = (ImageButton) root.findViewById(R.id.breakfast);
        lunch = (ImageButton) root.findViewById(R.id.lunch);
        dinner = (ImageButton) root.findViewById(R.id.dinner);
        fiber = (ImageButton) root.findViewById(R.id.fiber);
        drink = (ImageButton) root.findViewById(R.id.drink);
        menu = (ImageButton) root.findViewById(R.id.imageButton);
        menu1 = (ImageButton) root.findViewById(R.id.imageButton1);
        menu2 = (ImageButton) root.findViewById(R.id.imageButton2);
        menu3 = (ImageButton) root.findViewById(R.id.imageButton3);
        viewPagerImage = root.findViewById(R.id.viewPager2Image);

        cardView.setCardBackgroundColor(Color.TRANSPARENT);

        //THIS IS FOR HANDLING SLIDER ON FEATURED DISH
        int[] images = { R.drawable.c_slide_learntocook, R.drawable.c_slide_areyouondiet, R.drawable.c_slide_carvingforsteak};
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

        adapter = new VideoAdapter(videoList);
        viewPagerReels.setAdapter(adapter);

        videoList = new ArrayList<>();
        viewPagerReels = root.findViewById(R.id.viewPagerReels1);

        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.octo, "Eating", "Seafood is the best."));

        adapter = new VideoAdapter(videoList);
        viewPagerReels.setAdapter(adapter);

        CardView reelsCardView = root.findViewById(R.id.reelsCardView);
        reelsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_HomeFragment_to_ReelsFragment);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });

        return root;
    }

    public void openMenuPage() {
        Intent menu = new Intent(CHomeFragment.this.getActivity(), FoodRecipes.class);
        startActivity(menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}