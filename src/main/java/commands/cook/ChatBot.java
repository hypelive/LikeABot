package commands.cook;

import bot.Bot;
import bot.Status;
import com.google.gson.annotations.Expose;
import java.util.*;

public class ChatBot {
    private final String bundleBaseName = "commands.cook.ProgramResources";
    private String name;
    private boolean alive;
    private Locale locale = new Locale("ru");
    private static HashMap<String, Locale> locales = new HashMap<>();
    static {
        locales.put("en", new Locale("en"));
        locales.put("ru", new Locale("ru"));
    }
    @Expose(serialize = false, deserialize = false)
    private ResourceBundle resources2 = ResourceBundle.getBundle(bundleBaseName, locale);

    public HashMap<String, Command2> commands2 = new HashMap<>();

    public String echo2(Bot bot, String txt) {
        return txt;
    }

    public String quit2(Bot bot, String txt){
        bot.statusActive = Status.MENU;
        String out = commands2.get(txt).getDescription(resources2);
        return out;
    }

    public String getName2(Bot bot, String txt) {
        return name;
    }

    public String getInfo2(Bot bot, String txt) {
        return "Developed by Nikolay and Inna\nversion: 0.3";
    }

    public String help2(Bot bot, String txt) {
        StringBuilder result = new StringBuilder();
        for (String key : commands2.keySet()) {
            result.append(key);
            result.append(" - ");
            result.append(commands2.get(key).getDescription(resources2));
            result.append("\n");
        }
        return result.toString();
    }

    public String getHolidayFood2(Bot bot, String arg) { //also we can do Livenstein distance support
        StringBuilder str = new StringBuilder();
        HashMap<String, Food> holidayFood = (HashMap<String, Food>)resources2.getObject("hashM");
        if (holidayFood.get(arg) == null) {
            str.append(resources2.getString("avVar"));
            int counter = 0;
            for (String holiday : holidayFood.keySet()) {
                if (holiday.contains(arg)) {
                    str.append(holiday);
                    str.append("\n");
                    counter += 1;
                }
            }
            str.append(resources2.getString("sum")).append(counter).append(resources2.getString("var"));
        } else {
            str.append(holidayFood.get(arg).name);
            str.append('\n');
            str.append(getDescription2(holidayFood.get(arg)));
        }
        return str.toString();
    }

    public Food getFood(String arg){
        HashMap<String, Food> food = (HashMap<String, Food>) resources2.getObject("hashF");
        return food.get(arg);
    }

    public String changeLanguage2(Bot bot, String arg){
        if (!locales.containsKey(arg))
            return (String) resources2.getObject("unsupported lang");
        this.locale = locales.get(arg);
        resources2 = ResourceBundle.getBundle(bundleBaseName, this.locale);
        return resources2.getObject("current lang") + this.locale.toString();
    }

    public String startCook2(Bot bot, String foodName){
        // go to organizer and add recipe to schedule
        return "not implemented yet";
    }

    public boolean isAlive() {
        return this.alive;
    }

    public synchronized String getDescription2(Food food){ // if we have description then ok
        // if we don't have then find it in wiki
        StringBuilder res = new StringBuilder();
        if (food.description.equals("")){
            food.description = Parser.getDescriptionFromInternet(food.name, resources2);
            DataBase.setInDB(food);
        }
        res.append(food.description);
        if (food.recipeSteps != null) {
            res.append("\n");
            res.append((String) resources2.getObject("have recipe"));
        }
        return res.toString();
    }

    public String getResponse2(Bot bot, String input)
    {
        String name = input.split(" ")[0];
        String arg = "";
        if (input.length() >= 2)
            arg = input.substring(input.indexOf(" ") + 1);
        String result = (String) resources2.getObject("unknown cmd");
        if (commands2.containsKey(name)) {
            result = commands2.get(name).func2.apply(bot, arg.toLowerCase());
        }
        return result;
    }

    public String start(Bot bot, String input)
    {
        bot.statusActive = Status.COOK2;
        return (String) resources2.getObject("can i help");
    }

    public ChatBot(String name) {
        this.name = name;
        this.alive = true;

        commands2.put("echo", new Command2("echo", this::echo2));
        commands2.put("name", new Command2("name", this::getName2));
        commands2.put("info", new Command2("info", this::getInfo2));
        commands2.put("help", new Command2("help", this::help2));
        commands2.put("holiday", new Command2("holiday", this::getHolidayFood2));
        commands2.put("language", new Command2("language", this::changeLanguage2));
        commands2.put("cook", new Command2("cook", this::startCook2));
        commands2.put("quit", new Command2("quit", this::quit2));
    }

}