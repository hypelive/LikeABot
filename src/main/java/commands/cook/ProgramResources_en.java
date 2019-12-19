package commands.cook;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListResourceBundle;

public class ProgramResources_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashSet<String> tags = new HashSet<>();
        tags.add("butter");
        tags.add("egg");
        tags.add("milk");
        tags.add("sugar");
        tags.add("meal");
        tags.add("lemon");
        tags.add("spaghetti");
        tags.add("dough");
        tags.add("meat");
        tags.add("bean");
        tags.add("pepper");
        tags.add("chocolate");
        tags.add("cream");
        tags.add("cocoa");
        tags.add("nut");
        tags.add("potato");
        tags.add("cucumber");
        tags.add("sausage");
        tags.add("carrot");
        tags.add("tomato");
        tags.add("oat");
        HashMap food = new HashMap<>();
        try {
            food.put("olivier salad", new Food("olivier salad", "en"));
            food.put("mashed potato", new Food("mashed potato", "en"));
            food.put("spaghetti", new Food("spaghetti", "en"));
            food.put("cake", new Food("cake", "en"));
            food.put("pelmeni", new Food("pelmeni", "en"));
            food.put("turkey as food", new Food("turkey as food", "en"));
            food.put("pancake", new Food("pancake", "en"));
            food.put("pie", new Food("pie", "en"));
            food.put("porridge", new Food("porridge", "en"));
            food.put("borscht", new Food("borscht", "en"));
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
        return new Object[][] {{"hashM", holidayFood}, {"hashF", food}, {"tags", tags},
                {"nfInf", "information about this food is not found"},
                {"findR", "you can find recipes here: "},
                {"url", "https://recipebook.io/recipes?key="},
                {"wiki", "https://en.wikipedia.org/wiki/"},
                {"avVar", "Available variants:\n"},
                {"sum", "summary "},
                {"var", " variants"},// from here
                {"unsupported lang", "unsupported language"},
                {"current lang", "Current language: "},
                {"unknown cmd", "This command is undefined"},
                {"can i help", "you can ask me about any food"},
                {"echo", "repeat your text"},
                {"name", "get name of bot"},
                {"info", "get information about bot"},
                {"help", "get information about command"},
                {"holiday", "gives you information about food for holidays"},
                {"language", "change language"},
                {"cook", "start cook food with recipe"},
                {"have recipe", "we have a recipe for this, do you want to try?"},
                {"quit", "going back to the main menu"},
                {"take recipe", "Here's the recipe"},
                {"no food", "There is no such food"},
                {"timeout", "Time is out, the next step: "},
                {"done", "It's done!"},
                {"prepare", "You'll have 5 minutes to prepare to cook "},
                {"hereRec", "Here's the recipe of "},
                {"min", " minutes"},
                {"back", "Going back to the menu"},
                {"helpCook", "That's what I can do:\n* recipes - list all the recipes,\n"
                        + "* steps - list the steps of making the dish,\n"
                        + "* ingredients - list the ingredients of the dish,\n" +
                        "* start - start cooking,\n* find - find food by ingredients\n"
                        + "* quit - go back to the menu"},
                {"ihave", "That's what I have:\n"},
                {"cook start", "Hi! I'm the robotic cooking expert. I'm going " +
                        "to teach you all that I know. Write 'help' to learn " +
                        "more about what I can do."}};
    }
}
