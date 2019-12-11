package commands.cook;

import commands.TimeEvent;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import javax.imageio.IIOException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Food {
    public String name;
    public String description;

    public ArrayList<Pair<Integer, String>> recipeSteps = new ArrayList<>(); //тут пары типа : (15, "посолите воду")
                                                                             //где 15 минут от base time

    //так надо будет составить и добавить в органайзер рецепт

    public Food(String name) throws IOException {
        this.description = "";
        Food food = DataBase.searchInDB(name);
        this.name = name;
        if(food != null) {
            this.description = food.description;
            this.recipeSteps = food.recipeSteps;
        }
        if (recipeSteps == null){
            RecipeInitializer.initRecipe(this);
        }
    }

    //public ArrayList<string> ingridients;
}