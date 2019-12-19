package commands.cook;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RecipeInitializer {
    private static HashMap<String, String> recipeURLS = new HashMap<>();
    static {
        recipeURLS.put("borscht", "https://valentinascorner.com/beet-borscht-soup-recipe/");
        recipeURLS.put("olivier salad", "https://valentinascorner.com/olivier-salad-recipe-russian-potato-salad/");
        recipeURLS.put("mashed potato", "https://valentinascorner.com/the-best-mashed-potatoes-recipe/");
        recipeURLS.put("spaghetti", "https://valentinascorner.com/spaghetti-and-meatballs/");
        recipeURLS.put("cake", "https://valentinascorner.com/chocolate-spartak-cake-recipe/");
        recipeURLS.put("pelmeni", "https://valentinascorner.com/pelmeni-recipe-meat-dumplings/");
        recipeURLS.put("turkey as food", "https://valentinascorner.com/the-best-turkey-recipe/");
        recipeURLS.put("pancake", "https://valentinascorner.com/pancake-charcuterie-board/");
        recipeURLS.put("pie", "https://valentinascorner.com/easy-chicken-pot-pie/");
        recipeURLS.put("porridge", "https://valentinascorner.com/how-to-make-oatmeal/");

        recipeURLS.put("борщ", "https://www.gastronom.ru/recipe/47197/donskoj-borshch-s-ryboj");
        recipeURLS.put("оливье (салат)", "https://www.gastronom.ru/recipe/47852/olive-s-varenoj-kolbasoj");
        recipeURLS.put("картофельное пюре", "https://www.gastronom.ru/recipe/4079/klassicheskoe-pyshnoe-kartofelnoe-pyure");
        recipeURLS.put("спагетти", "https://www.gastronom.ru/recipe/46779/spagetti-s-kalmarami");
        recipeURLS.put("торт", "https://www.gastronom.ru/recipe/35575/medovik-osobennyj");
        recipeURLS.put("пельмени", "https://www.gastronom.ru/recipe/46680/pelmeni-govyadina-s-chernoslivom");
        recipeURLS.put("индюшатина", "https://www.gastronom.ru/recipe/24112/tushenaya-indejka");
        recipeURLS.put("блины", "https://www.gastronom.ru/recipe/31176/limonnye-bliny");
        recipeURLS.put("пудинг", "https://www.gastronom.ru/recipe/45828/idealnyj-risovyj-puding-s-mandarinami-i-shokoladom");
        recipeURLS.put("каша", "https://www.gastronom.ru/recipe/46768/ovsyanaya-kasha-malina-i-kokos");
    }
    private static HashMap<String, String> recipeURLSen = new HashMap<>();
    static {
        recipeURLSen.put("borscht", "https://valentinascorner.com/beet-borscht-soup-recipe/");
        recipeURLSen.put("olivier salad", "https://valentinascorner.com/olivier-salad-recipe-russian-potato-salad/");
        recipeURLSen.put("mashed potato", "https://valentinascorner.com/the-best-mashed-potatoes-recipe/");
        recipeURLSen.put("spaghetti", "https://valentinascorner.com/spaghetti-and-meatballs/");
        recipeURLSen.put("cake", "https://valentinascorner.com/chocolate-spartak-cake-recipe/");
        recipeURLSen.put("pelmeni", "https://valentinascorner.com/pelmeni-recipe-meat-dumplings/");
        recipeURLSen.put("turkey as food", "https://valentinascorner.com/the-best-turkey-recipe/");
        recipeURLSen.put("pancake", "https://valentinascorner.com/pancake-charcuterie-board/");
        recipeURLSen.put("pie", "https://valentinascorner.com/easy-chicken-pot-pie/");
        recipeURLSen.put("porridge", "https://valentinascorner.com/how-to-make-oatmeal/");
    }
    private static HashMap<String, String> recipeURLSru = new HashMap<>();
    static {
        recipeURLSru.put("борщ", "https://www.gastronom.ru/recipe/47197/donskoj-borshch-s-ryboj");
        recipeURLSru.put("оливье (салат)", "https://www.gastronom.ru/recipe/47852/olive-s-varenoj-kolbasoj");
        recipeURLSru.put("картофельное пюре", "https://www.gastronom.ru/recipe/4079/klassicheskoe-pyshnoe-kartofelnoe-pyure");
        recipeURLSru.put("спагетти", "https://www.gastronom.ru/recipe/46779/spagetti-s-kalmarami");
        recipeURLSru.put("торт", "https://www.gastronom.ru/recipe/35575/medovik-osobennyj");
        recipeURLSru.put("пельмени", "https://www.gastronom.ru/recipe/46680/pelmeni-govyadina-s-chernoslivom");
        recipeURLSru.put("индюшатина", "https://www.gastronom.ru/recipe/24112/tushenaya-indejka");
        recipeURLSru.put("блины", "https://www.gastronom.ru/recipe/31176/limonnye-bliny");
        recipeURLSru.put("пудинг", "https://www.gastronom.ru/recipe/45828/idealnyj-risovyj-puding-s-mandarinami-i-shokoladom");
        recipeURLSru.put("каша", "https://www.gastronom.ru/recipe/46768/ovsyanaya-kasha-malina-i-kokos");
    }

    public static void initRecipe(Food food)
    {
        if (recipeURLS.get(food.name) == null)
        {
            return;
        }
        ArrayList<Pair<Integer, String>> recipe = Parser.getRecipeFromInternet(recipeURLS.get(food.name), food.language);
        food.recipeSteps = recipe.subList(1, recipe.size());
        food.ingredients = (String) recipe.get(0).getValue(1);
    }

    public static Object[] getRecipesNames(Locale loc) {
        if (loc.getDisplayName().equals("русский"))
            return recipeURLSru.keySet().toArray();
        else
            return recipeURLSen.keySet().toArray();
    }

    public static List<Pair<Integer, String>> getRecipeSteps(Food food) {
        ArrayList<Pair<Integer, String>> recipe = Parser.getRecipeFromInternet(recipeURLS.get(food.name), food.language);
        return recipe.subList(1, recipe.size());
    }

    public static String getRecipeIngredients(Food food) {
        ArrayList<Pair<Integer, String>> recipe = Parser.getRecipeFromInternet(recipeURLS.get(food.name), food.language);
        return (String) recipe.get(0).getValue(1);
    }
}
