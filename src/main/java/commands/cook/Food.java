package commands.cook;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Food {
    public String name;
    public String language;
    public String description;

    public List<Pair<Integer, String>> recipeSteps = new ArrayList<Pair<Integer, String>>();
    public String ingredients;

    public Food(String name, String language) throws IOException {
        this.description = "";
        this.language = language;
        Food food = DataBase.searchInDB(name);
        this.name = name;
        if(food != null) {
            this.description = food.description;
            this.recipeSteps = food.recipeSteps;
            ingredients = food.ingredients;
        }
    }
}