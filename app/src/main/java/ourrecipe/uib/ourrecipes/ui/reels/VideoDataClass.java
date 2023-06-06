package ourrecipe.uib.ourrecipes.ui.reels;

public class VideoDataClass {
    private String videoURL;
    private String videoTitle;
    private String videoDescription;

    public VideoDataClass() {
        // Default constructor required for Firebase deserialization
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }
}
