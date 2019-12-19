package commands.cook;

import bot.Bot;
import bot.Status;
import org.javatuples.Pair;

import java.util.*;

public class ChatBot {
    private final static String bundleBaseName = "commands.cook.ProgramResources";
    private boolean alive;
    private static Locale locale = new Locale("ru");
    private static HashMap<String, Locale> locales = new HashMap<>();
    static {
        locales.put("en", new Locale("en"));
        locales.put("ru", new Locale("ru"));
    }
    private transient static ResourceBundle resources = ResourceBundle.getBundle(bundleBaseName, locale);

    public static HashMap<String, Command> commands = new HashMap<>();

    public String quit(Bot bot, String txt){
        bot.statusActive = Status.MENU;
        String out = commands.get(txt).getDescription(resources);
        return out;
    }

    public String help(Bot bot, String txt) {
        StringBuilder result = new StringBuilder();
        for (String key : commands.keySet()) {
            result.append(key);
            result.append(" - ");
            result.append(commands.get(key).getDescription(resources));
            result.append("\n");
        }
        return result.toString();
    }

    public static String getHolidayFood(Bot bot, String arg) { //also we can do Livenstein distance support
        StringBuilder str = new StringBuilder();
        HashMap<String, Food> holidayFood = (HashMap<String, Food>) resources.getObject("hashM");
        if (holidayFood.get(arg) == null) {
            str.append(resources.getString("avVar"));
            int counter = 0;
            for (String holiday : holidayFood.keySet()) {
                if (holiday.contains(arg)) {
                    str.append(holiday);
                    str.append("\n");
                    counter += 1;
                }
            }
            str.append(resources.getString("sum")).append(counter).append(resources.getString("var"));
        } else {
            str.append(holidayFood.get(arg).name);
            str.append('\n');
            str.append(getDescription(holidayFood.get(arg)));
        }
        return str.toString();
    }

    private static Food getFood(String arg){
        HashMap<String, Food> food = (HashMap<String, Food>) resources.getObject("hashF");
        return food.get(arg);
    }

    public static String changeLanguage(Bot bot, String arg){
        if (!locales.containsKey(arg))
            return (String) resources.getObject("unsupported lang");
        locale = locales.get(arg);
        resources = ResourceBundle.getBundle(bundleBaseName, locale);
        return resources.getObject("current lang") + locale.toString();
    }

    public static String startCook(Bot bot, String foodName){
        bot.statusActive = Status.COOK_ACTIVE;
        return resources.getObject("cook start").toString();
    }

    public static String helpCook(Bot bot, String input) {
        return "recipes, quit";
    }

    public static String getRecipes(Bot bot, String command) {
        String res = "This is what I have:\n";
        Object[] recipes = RecipeInitializer.getRecipesNames(locale);
        for (Object recipe : recipes) {
            res += (String) recipe + '\n';
        }
        return res;
    }

     public static String getFoodSteps(Bot bot, String command) {
        //steps [food]
         //TODO Exceptions!!
         String out = "<b>Here's the recipe of " + command.split(" ")[1] + ":</b>\n\n";
         Food food = getFood(command.split(" ")[1]);
         List<Pair<Integer, String>> steps = RecipeInitializer.getRecipeSteps(food);
         for (Pair<Integer, String> pair : steps) {
             out += "* " + pair.getValue(1) + " - " + pair.getValue(0) + " minutes.\n\n";
         }
         return out;
     }

     public static String getFoodIngredients(Bot bot, String command) {
        //ingredients [food]
         Food food = getFood(command.split(" ")[1]);
         return RecipeInitializer.getRecipeIngredients(food);
     }

    public static String quitCook(Bot bot, String command) {
        bot.statusActive = Status.COOK;
        return "going back to cook";
    }

    public boolean isAlive() {
        return this.alive;
    }

    public static synchronized String getDescription(Food food){ // if we have description then ok
        // if we don't have then find it in wiki
        StringBuilder res = new StringBuilder();
        if (food.description.equals("") || food.recipeSteps == null || food.ingredients == null){
            food.description = Parser.getDescriptionFromInternet(food.name, resources);
            RecipeInitializer.initRecipe(food);
            DataBase.setInDB(food);
        }
        res.append(food.description);
        if (food.recipeSteps != null) {
            res.append("\n");
            res.append((String) resources.getObject("have recipe"));
        }
        return res.toString();
    }

    public static String getResponse(Bot bot, String input)
    {
        String name = input.split(" ")[0];
        String arg = "";
        if (input.length() >= 2)
            arg = input.substring(input.indexOf(" ") + 1);
        String result = (String) resources.getObject("unknown cmd");
        if (commands.containsKey(name.toLowerCase())) {
            result = commands.get(name.toLowerCase()).func.apply(bot, arg.toLowerCase());
        }
        return result;
    }

    public static String start(Bot bot, String input)
    {
        bot.statusActive = Status.COOK;
        return (String) resources.getObject("can i help");
    }

    public ChatBot() {
        this.alive = true;

        commands.put("help", new Command("help", this::help));
        commands.put("holiday", new Command("holiday", ChatBot::getHolidayFood));
        commands.put("language", new Command("language", ChatBot::changeLanguage));
        commands.put("cook", new Command("cook",ChatBot::startCook));
        commands.put("quit", new Command("quit", this::quit));
    }

}