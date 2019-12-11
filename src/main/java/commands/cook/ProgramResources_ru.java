package commands.cook;

import java.io.IOException;
import java.util.HashMap;
import java.util.ListResourceBundle;

public class ProgramResources_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashMap<String, Food> food = new HashMap<String, Food>();
        try{
            food.put("оливье (салат)", new Food("оливье (салат)"));
            food.put("картофельное пюре", new Food("картофельное пюре"));
            food.put("спагетти", new Food("спагетти"));
            food.put("торт", new Food("торт"));
            food.put("пельмени", new Food("пельмени"));
            food.put("индюшатина", new Food("индюшатина"));
            food.put("блины", new Food("блины"));
            food.put("пирог", new Food("пирог"));
            food.put("пудинг", new Food("пудинг"));
            food.put("борщ", new Food("борщ"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Food> holidayFood = new HashMap<String, Food>();
        holidayFood.put("новый год", food.get("оливье (салат)"));
        holidayFood.put("день рождения", food.get("картофельное пюре"));
        holidayFood.put("день святого Валентина", food.get("спагетти"));
        holidayFood.put("первое сентября", food.get("торт"));
        holidayFood.put("рождество", food.get("пельмени"));
        holidayFood.put("день благодарения", food.get("индюшатина"));
        holidayFood.put("масленица", food.get("блины"));
        holidayFood.put("первое мая", food.get("пирог"));
        holidayFood.put("девятое мая", food.get("пудинг"));
        holidayFood.put("первое апреля", food.get("пирог"));
        holidayFood.put("день России", food.get("борщ"));
        return new Object[][] {{"hashM", holidayFood}, {"hashF", food},
                {"nfInf", "Информация не найдена"},
                {"findR", "вы можете найти рецепты здесь: "},
                {"url", "https://eda.ru/recipesearch?q="},
                {"wiki", "https://ru.wikipedia.org/wiki/"},
                {"avVar", "Возможные варианты:\n"},
                {"sum", "итого "},
                {"var", " вариантов"},
                {"unsupported lang", "неподдерживаемый язык"},
                {"current lang", "Текущий язык: "},
                {"unknown cmd", "Неизвестная команда"},
                {"can i help", "подсказать что-нибудь?"},
                {"echo", "эхо ответ"},
                {"name", "мое имя"},
                {"info", "информация обо мне"},
                {"help", "получить информацию о коммандах"},
                {"holiday", "подскажу еду на праздник"},
                {"language", "сменить язык"},
                {"cook", "начать готовить еду с рецептом"},
                {"have recipe", "у нас есть рецепт, хотите попробовать?"}};
    }
}
