package ourrecipe.uib.ourrecipes.ui.reels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.CFragmentReelsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CReelsFragment extends Fragment {
    private ViewPager2 viewPager2;
    private List<VideoDataClass> videoDataClassList;
    private VideoAdapter adapter;

    private CFragmentReelsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ReelsViewModel reelsViewModel = new ViewModelProvider(this).get(ReelsViewModel.class);

        binding = CFragmentReelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        videoDataClassList = new ArrayList<>();
        viewPager2 = root.findViewById(R.id.viewPager2);

        videoDataClassList.add(new VideoDataClass("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "This Looks Delicious."));
        videoDataClassList.add(new VideoDataClass("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "This Looks Delicious."));
        videoDataClassList.add(new VideoDataClass("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "This Looks Delicious."));

        adapter = new VideoAdapter(videoDataClassList);
        viewPager2.setAdapter(adapter);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}