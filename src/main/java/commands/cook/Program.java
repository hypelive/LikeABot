package commands.cook;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ChatBot bot = new ChatBot();
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
                    //result = bot.commands2.get(name).func2.apply(bot, arg.toLowerCase());
                } /*else if (name.equalsIgnoreCase("telegram")) {
                    TelegramBot.main(bot);
                    result = "telegram bot is started";
                }*/
                System.out.println(result);
        }

    }
}