package commands.cook;

import bot.Bot;
import bot.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ChatBot {
    private final String bundleBaseName = "commands.cook.ProgramResources";
    private String name;
    private String info = "Developed by Kirill and Nikolay\nversion: 0.2b";
    private boolean alive;
    //private static HashMap<String, Food> holidayFood;
    //private Languages language = Languages.ENGLISH;
    private Locale locale = new Locale("en");
    private static HashMap<String, Locale> locales = new HashMap<>();
    static {
        locales.put("en", new Locale("en"));
        locales.put("ru", new Locale("ru"));
    }
    @Expose(serialize = false, deserialize = false)
    private ResourceBundle resources = ResourceBundle.getBundle(bundleBaseName, locale);
    //private DataBase dataBase;

    public HashMap<String, Command> commands = new HashMap<>();

    /*static  {
        try{
            holidayFood = new HashMap<>();
            holidayFood.put("new year", new Food("Olivier salad"));
            holidayFood.put("birthday", new Food("Mashed potato"));
            holidayFood.put("valentine's day", new Food("Spaghetti"));
            holidayFood.put("1st september", new Food("Cake"));
            holidayFood.put("christmas", new Food("Pelmeni"));
            holidayFood.put("thanksgiving day", new Food("Turkey as food"));
            holidayFood.put("maslenitsa", new Food("Pancake"));
            holidayFood.put("1st may", new Food("Pie"));
            holidayFood.put("9th may", new Food("Porridge"));
            holidayFood.put("1st april", new Food("Pie"));
            holidayFood.put("russia day", new Food("Borscht"));
            holidayFood.put("новый год", new Food("Оливье (салат)"));
            holidayFood.put("день рождения", new Food("Картофельное пюре"));
            holidayFood.put("день святого Валентина", new Food("Спагетти"));
            holidayFood.put("первое сентября", new Food("Торт"));
            holidayFood.put("рождество", new Food("пельмени"));
            holidayFood.put("день благодарения", new Food("Индюшатина"));
            holidayFood.put("масленица", new Food("Блины"));
            holidayFood.put("первое мая", new Food("Пирог"));
            holidayFood.put("девятое мая", new Food("Пудинг"));
            holidayFood.put("первое апреля", new Food("Пирог"));
            holidayFood.put("день России", new Food("Борщ"));
            //holidayFood.put("test", new Food("non123"));
        }catch (IOException e) {
            System.exit(1);
        }
    }*/
    /*static {
        commands = new HashMap<>();
        commands.put("echo", Command("repeat your text", echo);
        commands.put("getName", getName);
        commands.put("help", help);
        commands.put("holiday", getHolidayFood);
    }*/

    public String echo(String txt) {
        return txt;
    }

    public String getName(String txt) {
        return name;
    }

    public String getInfo(String txt) {
        return info;
    }

    public String help(String txt) {
        StringBuilder result = new StringBuilder();
        for (String key : commands.keySet()) {
            result.append(key);
            result.append("- ");
            result.append(commands.get(key).getDescription(resources));
            result.append("\n");
        }
        return result.toString();
    }

    public String getHolidayFood(String arg) { //also we can do Livenstein distance support
        StringBuilder str = new StringBuilder();
        HashMap<String, Food> holidayFood = (HashMap<String, Food>)resources.getObject("hashM");
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

    public Food getFood(String arg){
        HashMap<String, Food> food = (HashMap<String, Food>) resources.getObject("hashF");
        return food.get(arg);
    }

    public String changeLanguage(String arg){
        if (!locales.containsKey(arg))
            return (String) resources.getObject("unsupported lang");
        this.locale = locales.get(arg);
        resources = ResourceBundle.getBundle(bundleBaseName, this.locale);
        return resources.getObject("current lang") + this.locale.toString();
    }

    public String startCook(String foodName){
        // go to organizer and add recipe to schedule
        return "not implemented yet";
    }

    public boolean isAlive() {
        return this.alive;
    }

    public synchronized String getDescription(Food food){ // if we have description then ok
        // if we don't have then find it in wiki
        StringBuilder res = new StringBuilder();
        if (food.description.equals("")){
            food.description = Parser.getDescriptionFromInternet(food.name, resources);
            DataBase.setInDB(food);
        }
        res.append(food.description);
        if (food.recipeSteps != null) {
            res.append("\n");
            res.append((String) resources.getObject("have recipe"));
        }
        return res.toString();
    }

    public String getResponse(Bot bot, String input)
    {
        String name = input.split(" ")[0];
        String arg = "";
        if (input.length() >= 2)
            arg = input.substring(input.indexOf(" ") + 1);
        String result = (String) resources.getObject("unknown cmd");
        if (commands.containsKey(name)) {
            result = commands.get(name).func.apply(arg.toLowerCase());
        }
        return result;
    }

    public String start(Bot bot, String input)
    {
        bot.statusActive = Status.COOK;
        return (String) resources.getObject("can i help");
    }

    public ChatBot(String name) {
        this.name = name;
        this.alive = true;
        commands.put("echo", new Command("echo", this::echo));
        commands.put("name", new Command("name", this::getName));
        commands.put("info", new Command("info", this::getInfo));
        commands.put("help", new Command("help", this::help));
        commands.put("holiday", new Command("holiday", this::getHolidayFood));
        commands.put("language", new Command("language", this::changeLanguage));
        commands.put("cook", new Command("cook", this::startCook));
    }
}