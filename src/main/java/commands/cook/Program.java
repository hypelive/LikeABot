package commands.cook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ChatBot bot = new ChatBot("Pavel");
        while (bot.isAlive()) {
                Scanner scan = new Scanner(System.in, "CP1251");
                String inp = scan.nextLine();
                /*try {
                    inp = new String(inp.getBytes("utf-8"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                //System.out.println(inp);
                String name = inp.split(" ")[0];
                String arg = "";
                if (inp.length() >= 2)
                    arg = inp.substring(inp.indexOf(" ") + 1);
                String result = "This command is undefined";
                if (bot.commands.containsKey(name)) {
                    result = bot.commands.get(name).func.apply(arg.toLowerCase());
                } /*else if (name.equalsIgnoreCase("telegram")) {
                    TelegramBot.main(bot);
                    result = "telegram bot is started";
                }*/
                System.out.println(result);
        }
    }
}