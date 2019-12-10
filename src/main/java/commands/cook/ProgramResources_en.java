package commands.cook;

import java.io.IOException;
import java.util.HashMap;
import java.util.ListResourceBundle;

public class ProgramResources_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashMap food = new HashMap<>();
        try {
            food.put("olivier salad", new Food("olivier salad"));
            food.put("mashed potato", new Food("mashed potato"));
            food.put("spaghetti", new Food("spaghetti"));
            food.put("cake", new Food("cake"));
            food.put("pelmeni", new Food("pelmeni"));
            food.put("turkey as food", new Food("turkey as food"));
            food.put("pancake", new Food("pancake"));
            food.put("pie", new Food("pie"));
            food.put("porridge", new Food("porridge"));
            food.put("pie", new Food("pie"));
            food.put("borscht", new Food("borscht"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap holidayFood = new HashMap<>();
        holidayFood.put("new year", food.get("olivier salad"));
        holidayFood.put("birthday", food.get("mashed potato"));
        holidayFood.put("valentine's day", food.get("spaghetti"));
        holidayFood.put("1st september", food.get("cake"));
        holidayFood.put("christmas", food.get("pelmeni"));
        holidayFood.put("thanksgiving day", food.get("turkey as food"));
        holidayFood.put("maslenitsa", food.get("pancake"));
        holidayFood.put("1st may", food.get("pie"));
        holidayFood.put("9th may", food.get("porridge"));
        holidayFood.put("1st april", food.get("pie"));
        holidayFood.put("russia day", food.get("borscht"));
        return new Object[][] {{"hashM", holidayFood}, {"hasF", food},
                {"nfInf", "information about this food is not found"},
                {"findR", "you can find recipes here: "},
                {"url", "https://recipebook.io/recipes?key="},
                {"wiki", "https://en.wikipedia.org/wiki/"},
                {"avVar", "Available variants:\n"},
                {"sum", "summary "},
                {"var", " variants"}};
    }
}
