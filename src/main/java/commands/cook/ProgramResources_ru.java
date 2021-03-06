package commands.cook;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListResourceBundle;

public class ProgramResources_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashSet<String> tags = new HashSet<>();
        tags.add("масл");
        tags.add("яйца");
        tags.add("яиц");
        tags.add("молок");
        tags.add("сахар");
        tags.add("мук");
        tags.add("лимон");
        tags.add("макарон");
        tags.add("тест");
        tags.add("мяс");
        tags.add("фасол");
        tags.add("перец");
        tags.add("перц");
        tags.add("шоколад");
        tags.add("слив");
        tags.add("какао");
        tags.add("фундук");
        tags.add("картофел");
        tags.add("огур");
        tags.add("колбас");
        tags.add("морков");
        tags.add("помидор");
        HashMap<String, Food> food = new HashMap<String, Food>();
        try{
            food.put("оливье (салат)", new Food("оливье (салат)", "ru"));
            food.put("картофельное пюре", new Food("картофельное пюре", "ru"));
            food.put("спагетти", new Food("спагетти", "ru"));
            food.put("торт", new Food("торт", "ru"));
            food.put("пельмени", new Food("пельмени", "ru"));
            food.put("индюшатина", new Food("индюшатина", "ru"));
            food.put("блины", new Food("блины", "ru"));
            food.put("пирог", new Food("пирог", "ru"));
            food.put("пудинг", new Food("пудинг", "ru"));
            food.put("борщ", new Food("борщ", "ru"));
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
        return new Object[][] {{"hashM", holidayFood}, {"hashF", food}, {"tags", tags},
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
                {"have recipe", "у нас есть рецепт, хотите попробовать?"},
                {"quit", "возврат в главное меню"},
                {"take recipe", "Держи рецепт"},
                {"helpCook", "Вот что я могу:\n* recipes - показать рецепты,\n"
                        + "* steps - показать рецепт по шагам,\n"
                        + "* ingredients - показать ингредиенты блюда,\n" +
                        "* start - начать готовить,\n* find - найти еду по продукту\n"
                        + "* quit - вернуться в меню"},
                {"ihave", "Вот что у меня есть:\n"},
                {"no food", "Такой еды нет"},
                {"timeout", "Время вышло, следующий шаг: "},
                {"done", "Готово!"},
                {"prepare", "Через пять минут начнем готовить "},
                {"hereRec", "Вот рецепт блюда "},
                {"min", " минут"},
                {"back", "Возврат в меню"},
                {"cook start", "Привет! Я интерактивный робот-помощник " +
                        "по хозяйству. Я обучу тебя всему, что знаю. " +
                        "Напиши 'help', чтобы узнать все мои возможности"}};
    }
}
