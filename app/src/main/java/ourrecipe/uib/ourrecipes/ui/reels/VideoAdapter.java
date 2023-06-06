package ourrecipe.uib.ourrecipes.ui.reels;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ourrecipe.uib.ourrecipes.R;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {



    List<VideoDataClass> videoDataClass;

    public VideoAdapter(List<VideoDataClass> videoDataClass) {
        this.videoDataClass = videoDataClass;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.c_fragment_reels_each_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoDataClass(videoDataClass.get(position));
    }

    @Override
    public int getItemCount() {
        return videoDataClass.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;
        TextView title, description;
        private ImageButton playButton;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            title = itemView.findViewById(R.id.video_title);
            description = itemView.findViewById(R.id.video_desc);

        }

        public void setVideoDataClass(VideoDataClass videoDataClass){
            videoView.setVideoPath(videoDataClass.getVideoUrl());
            title.setText(videoDataClass.getTitle());
            description.setText(videoDataClass.getDescription());

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    float videoRatio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
                    float screenRatio = videoView.getWidth() / (float) videoView.getHeight();

                    float scale = videoRatio / screenRatio;
                    if(scale >= 1f){
                        videoView.setScaleX(scale);
                    } else {
                        videoView.setScaleY(1f/scale);
                    }
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }
    }
}
