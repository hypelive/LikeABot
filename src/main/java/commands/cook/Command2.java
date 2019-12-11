package commands.cook;

import bot.Bot;

import java.util.ResourceBundle;
import java.util.function.BiFunction;

public class Command2 {
    public String name;
    public final BiFunction<Bot, String, String> func2;

    public Command2(String txt, BiFunction<Bot, String, String> f) {
        name = txt;
        func2 = f;
    }

    public String getDescription(ResourceBundle resources) {
        return (String) resources.getObject(name);
    }
}
