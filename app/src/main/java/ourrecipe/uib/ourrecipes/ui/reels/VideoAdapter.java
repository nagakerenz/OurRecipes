package ourrecipe.uib.ourrecipes.ui.reels;

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

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<VideoDataClass> videoList;
    private Context context;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    public VideoAdapter(List<VideoDataClass> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.c_fragment_reels_each_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoDataClass videoData = videoList.get(position);
        holder.videoTitleTextView.setText(videoData.getVideoTitle());
        holder.videoDescTextView.setText(videoData.getVideoDescription());

        // Show progress bar
        holder.progressBar.setVisibility(View.VISIBLE);

        playVideo(holder, videoData);
    }

    private void playVideo(final VideoViewHolder holder, VideoDataClass videoData) {
        holder.videoView.setVideoURI(Uri.parse(videoData.getVideoURL()));
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                isPlaying = true;

                // Hide progress bar
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.videoView.start();
    }

    private void pauseVideo(final VideoViewHolder holder) {
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
            videoView = itemView.findViewById(R.id.videoView);
            videoTitleTextView = itemView.findViewById(R.id.video_title);
            videoDescTextView = itemView.findViewById(R.id.video_desc);
            progressBar = itemView.findViewById(R.id.progressBar); // Add ProgressBar reference

        }
    }
}
