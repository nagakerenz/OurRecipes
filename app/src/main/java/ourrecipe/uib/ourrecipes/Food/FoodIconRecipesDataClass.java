package ourrecipe.uib.ourrecipes.Food;

public class FoodIconRecipesDataClass {
    private String title;
    private Double rating;
    private Long times;
    private String imageURL;
    private String parentKey;
    private String childKey;
    private Long liked;
    private boolean isFavorite;


    public FoodIconRecipesDataClass() {
        // Default constructor (required for Firebase)
    }

    public FoodIconRecipesDataClass(String title, Double rating, Long times, String imageURL, String parentKey, String childKey, Long liked, boolean isFavorite) {
        this.title = title;
        this.rating = rating;
        this.times = times;
        this.imageURL = imageURL;
        this.parentKey = parentKey;
        this.childKey = childKey;
        this.liked = liked;
        this.isFavorite = isFavorite;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public String getTimesText() {
        return times + " Minutes";
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getChildKey() {
        return childKey;
    }

    public void setChildKey(String childKey) {
        this.childKey = childKey;
    }

    public Long getLiked() {
        return liked;
    }

    public void setLiked(Long liked) {
        this.liked = liked;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
