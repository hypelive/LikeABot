package commands.cook;

import java.io.IOException;
import java.util.HashMap;

public class ChatBot {
    private String name;
    private String info = "Developed by Kirill and Nikolay\nversion: 0.2b";
    private boolean alive;
    private static HashMap<String, Food> holidayFood;
    //private DataBase dataBase;

    static  { //TODO dict with all foods and in holy foods; food from here
        try{
        holidayFood = new HashMap<String, Food>();
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
        holidayFood.put("test", new Food("non123"));
        }catch (IOException e) {
            System.exit(1);
        }
    }

    public HashMap<String, Command> commands = new HashMap<>();
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
            result.append(commands.get(key).description);
            result.append("\n");
        }
        return result.toString();
    }

    public String getHolidayFood(String arg) { //also we can do Livenstein distance support
        StringBuilder str = new StringBuilder();
        if (holidayFood.get(arg) == null) {
            str.append("Available variants:\n");
            int counter = 0;
            for (String holiday : holidayFood.keySet()) {
                if (holiday.contains(arg)) {
                    str.append(holiday);
                    str.append("\n");
                    counter += 1;
                }
            }
            str.append("summary ").append(counter).append(" variants");
        } else {
            str.append(holidayFood.get(arg).name);
            str.append('\n');
            str.append(getDescription(holidayFood.get(arg)));
        }
        return str.toString();
    }

    public boolean isAlive() {
        return this.alive;
    }

    public String getDescription(Food food){ // if we have description then ok
        // if we don't have then find it in wiki
        if (food.description.equals("")){
            food.description = Parser.getDescriptionFromInternet(food.name);
            DataBase.setInDB(food);
        }
        return food.description;
    }

    ChatBot(String name) {
        this.name = name;
        this.alive = true;
        commands.put("echo", new Command("repeat your text", this::echo));
        commands.put("name", new Command("get name of bot", this::getName));
        commands.put("info", new Command("get information about bot", this::getInfo));
        commands.put("help", new Command("get information about command", this::help));
        commands.put("holiday", new Command("gives you information about food for holidays", this::getHolidayFood));
    }
}