package ourrecipe.uib.ourrecipes.ui.reels;

public class Video {
    private String videoUrl;
    private String title;
    private String description;

    public Video(String videoUrl, String title, String description) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
