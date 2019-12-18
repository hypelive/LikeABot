package commands.cook;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class Food {
    public String name;
    public String description;

    public ArrayList<Pair<Integer, String>> recipeSteps = new ArrayList<>(); //тут пары типа : (15, "посолите воду")
                                                                             //где 15 минут от base time
    public String ingredients;

    //так надо будет составить и добавить в органайзер рецепт

    public Food(String name) throws IOException {
        this.description = "";
        Food food = DataBase.searchInDB(name);
        this.name = name;
        if(food != null) {
            this.description = food.description;
            this.recipeSteps = food.recipeSteps;
            ingredients = food.ingredients;
        }
        if (recipeSteps == null || ingredients == null){
            RecipeInitializer.initRecipe(this);
        }
    }

    //public ArrayList<string> ingredients;
}