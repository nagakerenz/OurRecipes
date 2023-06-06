package ourrecipe.uib.ourrecipes.ui.home;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.ui.reels.VideoDataClass;

public class VideoAdapterHome extends RecyclerView.Adapter<ourrecipe.uib.ourrecipes.ui.home.VideoViewHolder> {
    private List<VideoDataClass> videoList;
    private Context context;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean showTitleAndDescription; // Flag to indicate whether to display title and description or not

    public VideoAdapter(List<VideoDataClass> videoList, Context context, boolean showTitleAndDescription    ) {
        this.videoList = videoList;
        this.context = context;
        this.showTitleAndDescription = showTitleAndDescription;

    }

    @NonNull
    @Override
    public ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.c_fragment_reels_each_video, parent, false);
        return new ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter.VideoViewHolder holder, int position) {
//        VideoDataClass videoData = videoList.get(position);
//        String videoURL = videoData.getVideoURL();
//
//        holder.videoTitleTextView.setText(videoData.getVideoTitle());
//        holder.videoDescTextView.setText(videoData.getVideoDescription());
//        playVideo(holder, videoData);

        VideoDataClass videoData = videoList.get(position);
        String videoURL = videoData.getVideoURL();

//        playVideo(holder, videoURL);

        playVideo(holder, videoData);

        // Set title and description visibility based on the flag
        if (showTitleAndDescription) {
            holder.videoTitleTextView.setVisibility(View.VISIBLE);
            holder.videoDescTextView.setVisibility(View.VISIBLE);
            holder.videoTitleTextView.setText(videoData.getVideoTitle());
            holder.videoDescTextView.setText(videoData.getVideoDescription());
        } else {
            holder.videoTitleTextView.setVisibility(View.GONE);
            holder.videoDescTextView.setVisibility(View.GONE);
        }

        holder.progressBar.setVisibility(View.VISIBLE);

    }

    private void playVideo(final ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter.VideoViewHolder holder, VideoDataClass videoData) {
        holder.videoView.setVideoURI(Uri.parse(videoData.getVideoURL()));
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                isPlaying = true;

                holder.progressBar.setVisibility(View.GONE);

            }
        });
        holder.videoView.start();
    }

    private void pauseVideo(final ourrecipe.uib.ourrecipes.ui.reels.VideoAdapter.VideoViewHolder holder) {
        holder.videoView.pause();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        isPlaying = false;
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
        TextView videoTitleTextView;
        TextView videoDescTextView;
        ProgressBar progressBar;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoTiktok);
            videoTitleTextView = itemView.findViewById(R.id.video_title);
            videoDescTextView = itemView.findViewById(R.id.video_desc);
            progressBar = itemView.findViewById(R.id.progressBar); // Add ProgressBar reference

        }
    }
}
