package ourrecipe.uib.ourrecipes.Food.viewPager2;

public class IngredientFragmentDataClass {

    private String image;
    private String name;
    private String portion;

    public IngredientFragmentDataClass(String image, String name, String portion) {
        this.image = image;
        this.name = name;
        this.portion = portion;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPortion() {
        return portion;
    }
}
