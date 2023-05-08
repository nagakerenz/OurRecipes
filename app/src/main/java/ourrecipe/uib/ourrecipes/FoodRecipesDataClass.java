package ourrecipe.uib.ourrecipes;

public class FoodRecipesDataClass {

    private String id;
    private String name;
    private int rating;
    private int makingTime;
    private boolean favorite;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getMakingTime() {
        return makingTime;
    }

    public boolean isFavorite() {
        return favorite;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setMakingTime(int makingTime) {
        this.makingTime = makingTime;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


}
