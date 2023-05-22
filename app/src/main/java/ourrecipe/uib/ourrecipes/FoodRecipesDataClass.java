package ourrecipe.uib.ourrecipes;

import java.util.List;

public class FoodRecipesDataClass {

    private String id;
    private String foodImage;
    private String foodName;
    private int foodRating;
    private int foodTime;
    private boolean favorite;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private List<String> tags;


    // Add a no-argument constructor
    public FoodRecipesDataClass() {
        // Default constructor required for Firebase
    }
    // Constructor
    public FoodRecipesDataClass(String id, String foodImage, String foodName, int foodRating, int foodTime, boolean favorite,
                                String description, List<String> ingredients, List<String> steps, List<String> tags) {
        this.id = id;
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodRating = foodRating;
        this.foodTime = foodTime;
        this.favorite = favorite;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.tags = tags;
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

    public int getFoodRating() {
        return foodRating;
    }

    public void setFoodRating(int foodRating) {
        this.foodRating = foodRating;
    }

    public int getFoodTime() {
        return foodTime;
    }

    public void setFoodTime(int foodTime) {
        this.foodTime = foodTime;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
