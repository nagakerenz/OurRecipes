package ourrecipe.uib.ourrecipes.Food;

import java.util.List;

public class FoodRecipesDataClass {

    private String parentCategoryKey;
    private String parentKey;
    private String imageURL;
    private String name;
    private Long times;
    private List<String> nutrition;
    private String rating;
    private String servingSize;
    private String description;
    private List<String> ingredients;
    private List<String> ingredientPictures;
    private List<String> steps;

    // Constructor
    public FoodRecipesDataClass(String parentCategoryKey, String parentKey, String imageURL, String name, Long times,
                                List<String> nutrition, String rating, String servingSize, String description,
                                List<String> ingredients, List<String> ingredientPictures, List<String> steps) {
        this.parentCategoryKey = parentCategoryKey;
        this.parentKey = parentKey;
        this.imageURL = imageURL;
        this.name = name;
        this.times = times;
        this.nutrition = nutrition;
        this.rating = rating;
        this.servingSize = servingSize;
        this.description = description;
        this.ingredients = ingredients;
        this.ingredientPictures = ingredientPictures;
        this.steps = steps;
    }

    // Getter and Setter methods
    public String getParentCategoryKey() {
        return parentCategoryKey;
    }

    public void setParentCategoryKey(String parentCategoryKey) {
        this.parentCategoryKey = parentCategoryKey;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public List<String> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<String> nutrition) {
        this.nutrition = nutrition;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
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

    public List<String> getIngredientPictures() {
        return ingredientPictures;
    }

    public void setIngredientPictures(List<String> ingredientPictures) {
        this.ingredientPictures = ingredientPictures;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
