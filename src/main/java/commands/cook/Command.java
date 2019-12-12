package commands.cook;

import bot.Bot;

import java.util.ResourceBundle;
import java.util.function.BiFunction;

public class Command {
    public String name;
    public final BiFunction<Bot, String, String> func;

    public Command(String txt, BiFunction<Bot, String, String> f) {
        name = txt;
        func = f;
    }

    public String getDescription(ResourceBundle resources) {
        return (String) resources.getObject(name);
    }
}
