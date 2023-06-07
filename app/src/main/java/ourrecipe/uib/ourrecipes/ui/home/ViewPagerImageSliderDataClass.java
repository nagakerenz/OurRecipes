package ourrecipe.uib.ourrecipes.ui.home;

public class ViewPagerImageSliderDataClass {

    String imageURL;
//    String heading, description;

    public ViewPagerImageSliderDataClass(String imageURL) { //, String heading, String description
        this.imageURL = imageURL;
//        this.heading = heading;
//        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
