package ourrecipe.uib.ourrecipes.ui.reels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentReelsBinding;

public class ReelsFragment extends Fragment {
    private ViewPager2 viewPager2;
    private List<Video> videoList;
    private VideoAdapter adapter;

    private FragmentReelsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReelsViewModel reelsViewModel =
                new ViewModelProvider(this).get(ReelsViewModel.class);

        binding = FragmentReelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        videoList = new ArrayList<>();
        viewPager2 = root.findViewById(R.id.viewPager2);

        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.meat, "Meat", "GRILL All you can eat."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "This Looks Delicious."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.steak, "Steak", "Medium Rare."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.cook, "Cooking", "Spicy."));
        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.octo, "Octopus", "Seafood is the best."));


        adapter = new VideoAdapter(videoList);
        viewPager2.setAdapter(adapter);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}