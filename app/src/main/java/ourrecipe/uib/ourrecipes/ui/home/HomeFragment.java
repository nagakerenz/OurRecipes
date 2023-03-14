package ourrecipe.uib.ourrecipes.ui.home;


import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.FoodPage.BreakfastPage;
import ourrecipe.uib.ourrecipes.FoodPage.DinnerPage;
import ourrecipe.uib.ourrecipes.FoodPage.DrinkPage;
import ourrecipe.uib.ourrecipes.FoodPage.FiberPage;
import ourrecipe.uib.ourrecipes.FoodPage.LunchPage;
import ourrecipe.uib.ourrecipes.MenuResult;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentHomeBinding;
import ourrecipe.uib.ourrecipes.ui.reels.Video;
import ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter;

public class HomeFragment extends Fragment {
//    MaterialButton reels;
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
    private ViewPager2 viewPager2;
    private ViewPager2 viewPager3;
    private List<Video> videoList;
    private VideoAdapter adapter;
    public HomeFragment(){

    }

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        breakfast = (ImageButton) root.findViewById(R.id.breakfast);
        lunch = (ImageButton) root.findViewById(R.id.lunch);
        dinner = (ImageButton) root.findViewById(R.id.dinner);
        fiber = (ImageButton) root.findViewById(R.id.fiber);
        drink = (ImageButton) root.findViewById(R.id.drink);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBreakfastPage();
            }
        });
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLunchPage();
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDinnerPage();
            }
        });
        fiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFiberPage();
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrinkPage();
            }
        });

//        reels = (Button) root.findViewById(R.id.reels);
//        reels.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
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
        viewPager2 = root.findViewById(R.id.viewPager2);

        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "This Looks Delicious."));

        adapter = new VideoAdapter(videoList);
        viewPager2.setAdapter(adapter);

        videoList = new ArrayList<>();
        viewPager3 = root.findViewById(R.id.viewPager3);

        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.octo, "Eating", "Seafood is the best."));

        adapter = new VideoAdapter(videoList);
        viewPager3.setAdapter(adapter);

        menu = (ImageButton) root.findViewById(R.id.imageButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu1 = (ImageButton) root.findViewById(R.id.imageButton1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu2 = (ImageButton) root.findViewById(R.id.imageButton2);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu3 = (ImageButton) root.findViewById(R.id.imageButton3);
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });

        return root;
    }

    public void openBreakfastPage() {
        Intent breakfast = new Intent(HomeFragment.this.getActivity(), BreakfastPage.class);
        startActivity(breakfast);
    }

    public void openLunchPage(){
        Intent lunch = new Intent(HomeFragment.this.getActivity(), LunchPage.class);
        startActivity(lunch);
    }

    public void openDinnerPage(){
        Intent dinner = new Intent(HomeFragment.this.getActivity(), DinnerPage.class);
        startActivity(dinner);
    }

    public void openFiberPage(){
        Intent fiber = new Intent(HomeFragment.this.getActivity(), FiberPage.class);
        startActivity(fiber);
    }

    public void openDrinkPage() {
        Intent drink = new Intent(HomeFragment.this.getActivity(), DrinkPage.class);
        startActivity(drink);
    }


    public void openMenuPage() {
        Intent menu = new Intent(HomeFragment.this.getActivity(), MenuResult.class);
        startActivity(menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}