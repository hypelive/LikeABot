package commands.cook;

import bot.Bot;
import bot.Status;
import commands.organizer.OrganizerElement;
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

    public static String helpCook(Bot bot, String input) {
        return "recipes, steps, ingredients, start, quit";
    }

    public static String getRecipes(Bot bot, String command) {
        String res = "This is what I have:\n";
        Object[] recipes = RecipeInitializer.getRecipesNames(locale);
        for (Object recipe : recipes) {
            res += (String) recipe + '\n';
        }
        return res;
    }

    public static String getFoodByName(Bot bot, String command) {
        String food = command.substring(5);
        String out = "";
        HashSet<String> tags = (HashSet<String>)resources.getObject("tags");
        while (food.length() != 0) {
            if (tags.contains(food))
                break;
            food = food.substring(0, food.length() - 1);
        }
        if (food.equals(""))
            return "not correct";
        HashMap<String, Food> foods = (HashMap<String, Food>) resources.getObject("hashF");
        for (String curFood : foods.keySet()) {
            if (foods.get(curFood).ingredients != null && foods.get(curFood).ingredients.contains(food)) {
                out += foods.get(curFood).name + "\n";
            }
        }
        return out;
    }

    public static String checkRecipesDeadlines(Bot bot) {
        String output = "";
        String nextTask = "";
        for (int i = 0; i < bot.recipesSteps.size(); i++) {
            String key = bot.recipesSteps.get(i).date.getTimeInMillis()
                    + bot.recipesSteps.get(i).task;
            updateDeadlines(key, bot);
            if (checkIfSend(bot, key, bot.recipesSteps.get(i))) {
                try {
                    nextTask = bot.recipesSteps.get(i + 1).task;
                    output = "время вышло, следующий шаг: " + nextTask;
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    output = bot.recipesSteps.get(i).task;
                }
            }
        }
        return output;
    }

    private static void updateDeadlines(String key, Bot bot) {
        if (!bot.deadlinesRecipes.containsKey(key)) {
            HashMap<Integer, Boolean> times = new HashMap<>();
            times.put(0, false);
            bot.deadlinesRecipes.put(key, times);
        }
    }

    private static Boolean checkIfSend(Bot bot, String key, OrganizerElement e) {
        GregorianCalendar now = new GregorianCalendar();
        Long curTimeMs = now.getTimeInMillis();
        Long taskTimeMs = e.date.getTimeInMillis();
        Integer eps = 9000;
        Boolean shouldSend = Math.abs(taskTimeMs - curTimeMs) < eps;
        if (!bot.deadlinesRecipes.get(key).get(0) && shouldSend) {
            bot.deadlinesRecipes.get(key).put(0, true);
            return true;
        }
        return false;
    }

    public static String startCook(Bot bot, String command) {
        String out = "";
        Integer lastTime = 0;
        Food food = getFood(command.substring(6));
        getDescription(food);
        List<Pair<Integer, String>> steps = food.recipeSteps;
        bot.recipesSteps.clear();
        for (Pair<Integer, String> pair : steps) {
            bot.recipesSteps.add(makeElement(pair.getValue0(), pair.getValue1()));
            lastTime = pair.getValue0();
        }
        bot.recipesSteps.add(makeElement(lastTime + 1, "It's done!"));

        for (OrganizerElement e : bot.recipesSteps) {
            System.out.println(e.date.getTime() + " " + e.task + "\n");
        }
        return "You'll have 5 minutes to prepare to cook " + food.name;
    }

    public static String startCooking(Bot bot, String command) {
        bot.statusActive = Status.COOK_ACTIVE;
        return (String)resources.getObject("cook start");
    }

    private static OrganizerElement makeElement(Integer minutes, String task) {
        GregorianCalendar time = new GregorianCalendar();
        time.add(Calendar.MINUTE, minutes);
        return new OrganizerElement(time, task);
    }

     public static String getFoodSteps(Bot bot, String command) {
         String out = "<b>Here's the recipe of " + command.split(" ")[1] + ":</b>\n\n";
         try {
             Food food = getFood(command.substring(6));
             getDescription(food);
             List<Pair<Integer, String>> steps = food.recipeSteps;
             for (Pair<Integer, String> pair : steps) {
                 out += "* " + pair.getValue(1) + " - " + pair.getValue(0) + " minutes.\n\n";
             }
             return out;
         } catch (NullPointerException e) {
             return "no food like this";
         }
     }

     public static String getFoodIngredients(Bot bot, String command) {
         Food food = getFood(command.substring(12));
         getDescription(food);
         return food.ingredients;
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
        commands.put("cook", new Command("cook",ChatBot::startCooking));
        commands.put("quit", new Command("quit", this::quit));
    }

}