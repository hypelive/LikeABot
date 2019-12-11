package commands.cook2;

import bot.Bot;
import bot.Status;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class Cooker {
    /*
    private final String bundleBaseName = "commands.cook.ProgramResources";
    private String name;
    private String info = "Developed by Nikolay and Inna\nversion: 0.1";
    private boolean alive;
    private Locale locale = new Locale("en");
    private static HashMap<String, Locale> locales = new HashMap<>();
    static {
        locales.put("en", new Locale("en"));
        locales.put("ru", new Locale("ru"));
    }
    @Expose(serialize = false, deserialize = false)
    private ResourceBundle resources = ResourceBundle.getBundle(bundleBaseName, locale);
    public HashMap<String, Command> commands = new HashMap<>();
     */

    public static String showDefault(Bot bot, String input){
        return "Введи help, чтобы узнать обо всем, что я умею.";
    }

    public static String start(Bot bot, String input){
        bot.statusActive = Status.COOK;
        return "Welcome to giant! If you have a bonus card, please scan it now.";
    }

    public static String help(Bot bot, String input){
        return "Здесь будет хелп";
    }

    public static String quit(Bot bot, String input){
        bot.statusActive = Status.MENU;
        return "Возвращаемся в главное меню!";
    }
}
