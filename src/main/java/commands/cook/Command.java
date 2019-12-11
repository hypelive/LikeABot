package commands.cook;

import java.util.ResourceBundle;
import java.util.function.Function;

public class Command {
    public String description;
    public final Function<String, String> func;

    public Command(String txt, Function<String, String> f) {
        description = txt;
        func = f;
    }

    public String getDescription(ResourceBundle resoures) {
        return " "; //TODO
    }
}
