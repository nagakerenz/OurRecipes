package ourrecipe.uib.ourrecipes.ui.home.Categories;

public class CategoriesFoodIconDataClass {

    private String imageIconURL, rating, liked, foodName, time;

    public CategoriesFoodIconDataClass() {

    }

    public String getImageIconURL() {
        return imageIconURL;
    }

    public void setImageIconURL(String imageIconURL) {
        this.imageIconURL = imageIconURL;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CategoriesFoodIconDataClass(String imageIconURL, String rating, String liked, String foodName, String time) {
        this.imageIconURL = imageIconURL;
        this.rating = rating;
        this.liked = liked;
        this.foodName = foodName;
        this.time = time;
    }
}
