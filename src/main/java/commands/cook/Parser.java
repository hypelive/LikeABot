package commands.cook;

import org.checkerframework.checker.regex.qual.Regex;
import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static HashMap<String, HashMap<String, String>> recipeParseRegexps = new HashMap<>();
    static {
        HashMap<String, String> regexps_en = new HashMap<>();
        regexps_en.put("ingredientPtr", "\"recipeIngredient\":\\[.*?]");
        regexps_en.put("ingredientRep1", "\"recipeIngredient\":\\[\"");
        regexps_en.put("ingredientRep2", "\",\"");
        regexps_en.put("ingredientRep3", "\"]");
        regexps_en.put("stepPtr", "\\{\"@type\":\"HowToStep\",\"text\":.*?}");
        regexps_en.put("stepRep1", "\\{\"@type\":\"HowToStep\",\"text\":\"");
        regexps_en.put("stepRep2", "\"}");
        regexps_en.put("timePtr", "(\\d+?) min");
        recipeParseRegexps.put("en", regexps_en);

        HashMap<String, String> regexps_ru = new HashMap<>();
        regexps_ru.put("ingredientPtr", "<ul>\\s*<li class=\"recipe__ingredient\" itemprop=\"ingredients\">.*?</li>\\s*</ul>\\s*<div i");
        regexps_ru.put("ingredientRep1", "\\<ul>\\s*\\<li class=\"recipe__ingredient\" itemprop=\"ingredients\">");
        regexps_ru.put("ingredientRep2", "\\</li>.*?\\<li class=\"recipe__ingredient\" itemprop=\"ingredients\">");
        regexps_ru.put("ingredientRep3", "\\</li>\\s*\\</ul>.*?");
        regexps_ru.put("stepPtr", "\\<div class=\"recipe__step-text\">.*?\\</div>");
        regexps_ru.put("stepRep1", "\\<div class=\"recipe__step-text\">\\s+");
        regexps_ru.put("stepRep2", "\\s+?\\</div>");
        regexps_ru.put("timePtr", "(\\d+?) мин");
        recipeParseRegexps.put("ru", regexps_ru);
    }

    public static String getDescriptionFromInternet(String food, ResourceBundle res){ // if we have description then ok
        // if we don't have then find it in wiki
        StringBuilder description = new StringBuilder();
        String page = getPageWithDescription(food, res);
        Pattern pattern = Pattern.compile("<p>.*?<b>.+?</p>");
        Matcher matcher = pattern.matcher(page);
        if (!(matcher.find())){
            return res.getString("nfInf");
        }
        description.append(page.substring(matcher.start(), matcher.end())
                .replaceAll("<.+?>", "")
                .replaceAll("&#91.*?#93;", "")
                .replaceAll(".#.*?;", "")
                .replaceAll("\\?+?", ""));
        description.append("\n");
        description.append(res.getString("findR"));
        food = URLEncoder.encode(food, StandardCharsets.UTF_8);
        String recURL = res.getString("url");
        description.append(recURL).append(food.replaceAll(" ", "%20"));
        return description.toString();
    }

    private static String getPageWithDescription(String food, ResourceBundle res) {
        URL url;
        String page = "";
        food = URLEncoder.encode(food, StandardCharsets.UTF_8);
        food = food.replace('+', '_');
        String wiki = res.getString("wiki");
        return getPage(wiki + food.replace(' ', '_'));
    }

    public static ArrayList<Pair<Integer, String>> getRecipeFromInternet(String url, String lang)
    {
        ArrayList<Pair<Integer, String>> recipe = new ArrayList<>();
        String page = getPage(url);
        Pattern ingredientPattern = Pattern.compile(recipeParseRegexps.get(lang).get("ingredientPtr"));
        Matcher ingredientMatcher = ingredientPattern.matcher(page);
        ingredientMatcher.find();
        String ingredients = page.substring(ingredientMatcher.start(), ingredientMatcher.end())
                .replaceAll(recipeParseRegexps.get(lang).get("ingredientRep1"), "")
                .replaceAll(recipeParseRegexps.get(lang).get("ingredientRep2"), ",")
                .replaceAll(recipeParseRegexps.get(lang).get("ingredientRep3"), "");
        recipe.add(new Pair<>(0, ingredients));
        String[] matches = Pattern.compile(recipeParseRegexps.get(lang).get("stepPtr"))
                .matcher(page)
                .results()
                .map(MatchResult::group)
                .map((String step) -> step.replaceAll(recipeParseRegexps.get(lang).get("stepRep1"), "")
                                          .replaceAll(recipeParseRegexps.get(lang).get("stepRep2"), ""))
                .toArray(String[]::new);
        int minuteCounter = 5; //for preparing
        Pattern timePattern = Pattern.compile(recipeParseRegexps.get(lang).get("timePtr"));
        for (String step : matches)
        {
            recipe.add(new Pair<>(minuteCounter, step));
            Matcher matcher = timePattern.matcher(step);
            if (matcher.find())
            {
                minuteCounter += Integer.parseInt(matcher.group(1));
            }
            else
            {
                minuteCounter += 10;
            }
        }
        return recipe;
    }

    private static String getPage(String s_url) {
        URL url;
        String page = "";
        try {
            url = new URL(s_url);
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
