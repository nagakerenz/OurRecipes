package ourrecipe.uib.ourrecipes.ui.reels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private List<Video> videoList;
    private VideoAdapter adapter;

    private CFragmentReelsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ReelsViewModel reelsViewModel = new ViewModelProvider(this).get(ReelsViewModel.class);

        binding = CFragmentReelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        videoList = new ArrayList<>();
        viewPager2 = root.findViewById(R.id.viewPager2);

        // Fetch videos from the API
        fetchVideos();


//
//        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.meat, "Meat", "GRILL All you can eat."));
//        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.eat, "Eating", "This Looks Delicious."));
//        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.steak, "Steak", "Medium Rare."));
//        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.cook, "Cooking", "Spicy."));
//        videoList.add(new Video("android.resource://" + getContext().getPackageName() + "/" + R.raw.octo, "Octopus", "Seafood is the best."));
//
//
//        adapter = new VideoAdapter(videoList);
//        viewPager2.setAdapter(adapter);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchVideos() {
        // Create an instance of Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.example.com/")  // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API service
        VideoApiService apiService = retrofit.create(VideoApiService.class);

        // Make the API call
        Call<List<VideoResponse>> call = apiService.getVideos();
        call.enqueue(new Callback<List<VideoResponse>>() {
            @Override
            public void onResponse(Call<List<VideoResponse>> call, Response<List<VideoResponse>> response) {
                if (response.isSuccessful()) {
                    List<VideoResponse> videoResponses = response.body();
                    if (videoResponses != null) {
                        // Convert VideoResponse objects to Video objects
                        for (VideoResponse videoResponse : videoResponses) {
                            Video video = new Video(videoResponse.getVideoUrl(), videoResponse.getTitle(), videoResponse.getDescription());
                            videoList.add(video);
                        }

                        // Update the adapter with the new video data
                        adapter = new VideoAdapter(videoList);
                        viewPager2.setAdapter(adapter);
                    }
                } else {
                    // Handle API error
                    Toast.makeText(CReelsFragment.this.getContext(), "Failed to fetch videos: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<VideoResponse>> call, Throwable t) {
                // Handle API call failure
                Toast.makeText(CReelsFragment.this.getContext(), "Failed to fetch videos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}