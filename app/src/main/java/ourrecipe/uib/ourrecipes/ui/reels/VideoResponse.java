package ourrecipe.uib.ourrecipes.ui.reels;

import com.google.gson.annotations.SerializedName;

public class VideoResponse {

    @SerializedName("video_url")
    private String videoUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}