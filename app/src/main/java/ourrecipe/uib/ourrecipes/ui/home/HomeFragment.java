package ourrecipe.uib.ourrecipes.ui.home;


import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.BreakfastPage;
import ourrecipe.uib.ourrecipes.DinnerPage;
import ourrecipe.uib.ourrecipes.DrinkPage;
import ourrecipe.uib.ourrecipes.FiberPage;
import ourrecipe.uib.ourrecipes.LunchPage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentHomeBinding;
import ourrecipe.uib.ourrecipes.ui.reels.Video;
import ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter;

public class HomeFragment extends Fragment {
    ImageButton breakfast;
    ImageButton lunch;
    ImageButton dinner;
    ImageButton fiber;
    ImageButton drink;
    private ViewPager2 viewPager2;
    private List<Video> videoList;
    private VideoAdapter adapter;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        breakfast = (ImageButton) root.findViewById(R.id.breakfast);
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBreakfastPage();
            }
        });

        lunch = (ImageButton) root.findViewById(R.id.lunch);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLunchPage();
            }
        });
        dinner = (ImageButton) root.findViewById(R.id.dinner);
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDinnerPage();
            }
        });
        fiber = (ImageButton) root.findViewById(R.id.fiber);
        fiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFiberPage();
            }
        });
        drink = (ImageButton) root.findViewById(R.id.drink);
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrinkPage();
            }
        });
        videoList = new ArrayList<>();
        viewPager2 = root.findViewById(R.id.viewPager2);

        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "lorem ipsum dolor sit amet."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.steak, "Steak", "lorem ipsum dolor sit amet."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.cook, "Cooking", "lorem ipsum dolor sit amet."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.octo, "Octopus", "lorem ipsum dolor sit amet."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.meat, "Meat", "lorem ipsum dolor sit amet."));

        adapter = new VideoAdapter(videoList);
        viewPager2.setAdapter(adapter);

//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button even
//                Log.d("BACKBUTTON", "Back button clicks");
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}