package commands.cook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ChatBot bot = new ChatBot("Pavel");
        while (bot.isAlive()) {
            Scanner scan = new Scanner(System.in);
            String inp = scan.nextLine();
            String name = inp.split(" ")[0];
            String arg = "";
            if (inp.length() >= 2)
                arg = inp.substring(inp.indexOf(" ") + 1);
            String result = "This command is undefined";
            if (bot.commands.containsKey(name)) {
                result = bot.commands.get(name).func.apply(arg.toLowerCase());
            }
            /*else if(name.equalsIgnoreCase("telegram")) {
            	TelegramBot.main(new String[0]);
            	result = "telegram bot is started";
            }*/
            System.out.println(result);
        }
    }
}