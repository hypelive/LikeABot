package commands.cook;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeInitializer {
    private static HashMap<String, ArrayList<Pair<Integer, String>>> recipes = new HashMap<>();
    static {
        ArrayList<Pair<Integer, String>> recipeSteps = new ArrayList<>();
        recipeSteps.add(new Pair<>(0, "In a large pot, add potatoes and carrots. Cover completely with water. Bring to a boil and cook about 20-25 minutes or until a knife pierces through easily. (Don’t overcook. Remove carrots and potatoes from water, set aside to cool.)"));
        recipeSteps.add(new Pair<>(27, "In a small pot add eggs, cover eggs will water and bring to a boil cook. Cook eggs 8-10 minutes."));
        recipeSteps.add(new Pair<>(38, "Cube all of the ingredients."));
        recipeSteps.add(new Pair<>(42, "Combine everything, mix. Add mayo to taste. You may need a little more than 1 cup, depending on sizes of your ingredients. "));
        recipeSteps.add(new Pair<>(47, "Season with salt, pepper and sugar. Add fresh dill, mix."));
        recipes.put("olivier salad", recipeSteps);
    } //TODO add more recipes

    public static void initRecipe(Food food)
    {
        food.recipeSteps = recipes.get(food.name);
    }
}
