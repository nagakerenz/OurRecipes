package ourrecipe.uib.ourrecipes.ui.reels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.CFragmentReelsBinding;
import ourrecipe.uib.ourrecipes.ui.home.CHomeFragment;
import ourrecipe.uib.ourrecipes.ui.profile.CProfileFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CReelsFragment extends Fragment {
    private ViewPager2 viewPager2;
    private VideoAdapter videoAdapter;
    private ProgressBar progressBar; // Add ProgressBar reference

    private CFragmentReelsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ReelsViewModel reelsViewModel = new ViewModelProvider(this).get(ReelsViewModel.class);

        binding = CFragmentReelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = root.findViewById(R.id.viewPager2);
        progressBar = root.findViewById(R.id.progressBar); // Initialize ProgressBar

        // Create an empty adapter and set it on the ViewPager2
        videoAdapter = new VideoAdapter(new ArrayList<>(), CReelsFragment.this.getActivity(), true);
        viewPager2.setAdapter(videoAdapter);

        progressBar.setVisibility(View.VISIBLE);

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

                    // Create the adapter and pass the video data to it
                    videoAdapter = new VideoAdapter(videoList, CReelsFragment.this.getActivity(), true);

                    // Update the adapter with the video data
                    videoAdapter.updateVideoList(videoList);

                    // Set the adapter on the ViewPager2
                    viewPager2.setAdapter(videoAdapter);

                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Failed to retrieve video data", Toast.LENGTH_SHORT).show();
                }
            });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}