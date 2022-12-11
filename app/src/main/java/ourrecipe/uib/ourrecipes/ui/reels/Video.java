package ourrecipe.uib.ourrecipes.ui.reels;

public class Video {
    private String videoUrl , title , desc;

    public Video(String videoUrl, String title, String desc) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.desc = desc;

    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
