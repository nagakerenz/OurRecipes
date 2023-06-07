package ourrecipe.uib.ourrecipes.ui.home;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.ui.home.Categories.Categories;
import ourrecipe.uib.ourrecipes.ui.reels.CReelsFragment;
import ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter;
import ourrecipe.uib.ourrecipes.ui.reels.VideoDataClass;

public class VideoAdapterHome extends RecyclerView.Adapter<VideoAdapterHome.VideoViewHolder> {
    private List<VideoDataClass> videoList;
    private Context context;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean isSoundEnabled = true;

    public VideoAdapterHome(List<VideoDataClass> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoAdapterHome.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.c_fragment_home_each_video, parent, false);
        return new VideoAdapterHome.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterHome.VideoViewHolder holder, int position) {
        VideoDataClass videoData = videoList.get(position);

        // Show progress bar
        holder.progressBar.setVisibility(View.VISIBLE);

        playVideo(holder, videoData);

        if (holder.mediaPlayer != null) {
            if (isSoundEnabled) {
                holder.mediaPlayer.setVolume(1f, 1f); // Set volume to full
            } else {
                holder.mediaPlayer.setVolume(0f, 0f); // Set volume to mute
            }
        }
//       Set OnClickListener for the CardView
        holder.cardViewReelsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), Categories.class);
//                v.getContext().startActivity(intent);
                NavController navController = Navigation.findNavController(v);
                if (navController.getCurrentDestination().getId() != R.id.navigation_reels) {
                    navController.navigate(R.id.action_navigation_home_to_navigation_reels);
                }
            }
        });
    }



    private void playVideo(final VideoAdapterHome.VideoViewHolder holder, VideoDataClass videoData) {
        holder.videoView.setVideoURI(Uri.parse(videoData.getVideoURL()));
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setLooping(true);
                if (isSoundEnabled) {
                    mediaPlayer.setVolume(1f, 1f); // Set volume to full
                } else {
                    mediaPlayer.setVolume(0f, 0f); // Set volume to mute
                }

                mediaPlayer.start();
                isPlaying = true;

                // Hide progress bar
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.videoView.start();
    }

    private void pauseVideo(final VideoAdapterHome.VideoViewHolder holder) {
        holder.videoView.pause();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        isPlaying = false;
    }

    public void setSoundEnabled(boolean enbaled) {
        isSoundEnabled = enbaled;
        if (mediaPlayer != null) {
            if (isSoundEnabled) {
                mediaPlayer.setVolume(1f, 1f); // Set volume to full
            } else {
                mediaPlayer.setVolume(0f, 0f); // Set volume to mute
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        notifyDataSetChanged();
    }
    public void updateVideoList(List<VideoDataClass> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        MediaPlayer mediaPlayer;
        ProgressBar progressBar;
        CardView cardViewReelsHome;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            progressBar = itemView.findViewById(R.id.progressBar); // Add ProgressBar reference
            mediaPlayer = new MediaPlayer();
            cardViewReelsHome = itemView.findViewById(R.id.cardViewReelsHome);

        }
    }

}