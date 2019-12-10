package commands.cook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static String getDescriptionFromInternet(String food){ // if we have description then ok
        // if we don't have then find it in wiki
        StringBuilder description = new StringBuilder();
        String page = getPageWithDescription(food);
        Pattern pattern = Pattern.compile("<p>.*?<b>.+?</p>");
        Matcher matcher = pattern.matcher(page);
        if (!(matcher.find())){
            return "information about this food is not found";
        }
        description.append(page.substring(matcher.start(), matcher.end())
                .replaceAll("<.+?>", "")
                .replaceAll("&#91.*?#93;", "")
                .replaceAll(".#.*?;", ""));
        description.append("\n");
        description.append("you can find recipes here: ");
        description.append("https://recipebook.io/recipes?key=").append(food.replaceAll(" ", "%20"));

        return description.toString();
    }

    private static String getPageWithDescription(String food) {
        URL url;
        String page = "";
        try {
            url = new URL("https://en.wikipedia.org/wiki/" + food.replace(' ', '_'));
            URLConnection connection = url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder pageBuffer = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                pageBuffer.append(inputLine);
            }
            br.close();
            page = pageBuffer.toString();
        } catch (IOException e) {
            return "";
        }
        return page;
    }
}
