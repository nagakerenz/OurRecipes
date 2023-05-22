package ourrecipe.uib.ourrecipes.Food;

public class FoodRecipesIconDataClass {

    private String id;
    private String foodImage;
    private String foodName;
    private Long foodRating;
    private String foodTime;
    private boolean favorite;


    // Add a no-argument constructor
    public FoodRecipesIconDataClass() {
        // Default constructor required for Firebase
    }
    // Constructor
    public FoodRecipesIconDataClass(String id, String foodImage, String foodName, Long foodRating, String foodTime, boolean favorite) {
        this.id = id;
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodRating = foodRating;
        this.foodTime = foodTime;
        this.favorite = favorite;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Long getFoodRating() {
        return foodRating;
    }

    public void setFoodRating(Long foodRating) {
        this.foodRating = foodRating;
    }

    public String getFoodTime() {
        return foodTime;
    }

    public void setFoodTime(String foodTime) {
        this.foodTime = foodTime;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
