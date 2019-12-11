package commands.cook2;

import java.util.ResourceBundle;
import java.util.function.Function;

public class Command {
    public String name;
    public final Function<String, String> func;

    public Command(String txt, Function<String, String> f) {
        name = txt;
        func = f;
    }

    public String getDescription(ResourceBundle resources) {
        return (String) resources.getObject(name);
    }
}
