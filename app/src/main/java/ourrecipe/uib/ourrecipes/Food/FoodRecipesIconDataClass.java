package ourrecipe.uib.ourrecipes.Food;

public class FoodRecipesIconDataClass {
    private String id;
    private String title;
    private String rating;
    private Long times;
    private String imageURL;

    public FoodRecipesIconDataClass() {
        // Default constructor (required for Firebase)
    }

    public FoodRecipesIconDataClass(String id, String title, String rating, Long times, String imageURL) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.times = times;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Long gettimes() {
        return times;
    }

    public void settimes(Long times) {
        this.times = times;
    }

    public String getTimesText() {
        return times + " Minutes";
    }

    public String getimageURL() {
        return imageURL;
    }

    public void setimageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}