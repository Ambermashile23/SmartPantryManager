package com.example.smartpantrymanger;

public class RecipeSeeder {

    // Only seeds when the recipes table is empty (first run)
    public static void seedIfEmpty(AppDatabase db) {
        RecipeDao dao = db.recipeDao();
        if (dao.countRecipes() > 0) return;

        add(dao, "Sunday Kos",
                "Rub the chicken with oil and salt and roast for 1 hour 15 minutes.\nRoast the potato and pumpkin in the same oven until golden.\nBoil the rice until fluffy.\nSimmer the chicken juices with the stock and onion to make gravy and serve everything together.",
                "chicken|1|kg", "rice|300|g", "pumpkin|500|g", "potato|500|g", "onion|1|pcs", "stock|250|ml", "oil|30|ml", "salt|1|tsp");

        add(dao, "Pap and Wors",
                "Boil the water with salt and stir in the maize meal.\nCover and cook on low heat for 30 minutes.\nBraai or fry the boerewors until cooked through and serve with the pap.",
                "maize meal|500|g", "boerewors|500|g", "salt|1|tsp");

        add(dao, "Chakalaka",
                "Fry the onion in oil until soft.\nAdd the carrot, pepper, chilli and curry powder.\nStir in the tomato and baked beans and simmer for 20 minutes.",
                "onion|1|pcs", "carrot|2|pcs", "bell pepper|1|pcs", "chilli|1|pcs", "curry powder|1|tbsp", "tomato|400|g", "baked beans|410|g", "oil|30|ml");

        add(dao, "Durban Bunny Chow",
                "Fry the onion and curry powder in a pot.\nAdd the chicken, tomato and potato.\nSimmer for 40 minutes until tender.\nHollow out the loaf and fill it with the curry.",
                "bread|1|pcs", "chicken|500|g", "onion|1|pcs", "tomato|400|g", "potato|2|pcs", "curry powder|2|tbsp");

        add(dao, "Potjiekos",
                "Brown the lamb with the onion in a cast iron pot.\nAdd the stock and simmer for 1 hour.\nLayer in the carrot and potato and cook without stirring for another 45 minutes.",
                "lamb|1|kg", "potato|500|g", "carrot|3|pcs", "onion|2|pcs", "stock|500|ml");

        add(dao, "Melktert",
                "Heat the milk with the cinnamon.\nWhisk the eggs, sugar and flour together, then stir into the hot milk until thick.\nAdd the butter and pour into a baked pastry case.\nChill and dust with cinnamon.",
                "milk|500|ml", "flour|60|g", "sugar|100|g", "egg|2|pcs", "butter|30|g", "cinnamon|1|tsp");

        add(dao, "Koeksisters",
                "Make a dough with the flour, butter and egg.\nPlait the dough into strips and deep fry until golden.\nDip immediately in ice cold sugar syrup.",
                "flour|500|g", "sugar|1|kg", "butter|50|g", "egg|1|pcs", "oil|500|ml");

        add(dao, "Dombolo and Beef Stew",
                "Brown the beef with the onion in a heavy pot.\nAdd the tomato, carrot, potato and stock and simmer for 1 hour 30 minutes.\nMix the flour, yeast, sugar and salt into a soft dough and let it rise for 1 hour.\nShape into balls, place on top of the stew, cover and steam for 25 minutes.",
                "beef|800|g", "onion|2|pcs", "tomato|200|g", "carrot|3|pcs", "potato|500|g", "stock|500|ml", "flour|500|g", "yeast|10|g", "sugar|10|g", "salt|1|tsp");

        add(dao, "Boerewors Roll",
                "Braai the boerewors until cooked through.\nCook the onion and tomato into a quick relish.\nPlace the wors in a roll and top with the relish.",
                "boerewors|500|g", "bread roll|4|pcs", "onion|1|pcs", "tomato|2|pcs", "chutney|2|tbsp");

        add(dao, "Vetkoek and Mince",
                "Mix the flour, yeast and water into a dough and let it rise.\nDeep fry spoonfuls until golden.\nBrown the mince with the onion and tomato and spoon into the split vetkoek.",
                "flour|500|g", "yeast|10|g", "oil|500|ml", "mince|500|g", "onion|1|pcs", "tomato|200|g");

        add(dao, "Samp and Beans",
                "Soak the samp and beans overnight.\nBoil them together for 2 to 3 hours until soft.\nStir in the fried onion and butter and season.",
                "samp|500|g", "sugar bean|250|g", "onion|1|pcs", "butter|30|g");

        add(dao, "Malva Pudding",
                "Beat the sugar, eggs and apricot jam together.\nFold in the flour and milk and bake until risen.\nMelt the butter into the cream and pour over the hot pudding.",
                "sugar|200|g", "egg|2|pcs", "flour|250|g", "apricot jam|60|g", "milk|250|ml", "butter|50|g", "cream|250|ml");

        add(dao, "Snoek Braai",
                "Melt the butter with the apricot jam, garlic and lemon juice.\nBaste the snoek with the mixture.\nBraai for 15 minutes, turning once.",
                "snoek|1|kg", "apricot jam|100|g", "butter|50|g", "lemon|2|pcs", "garlic|2|pcs");

        add(dao, "Cape Malay Chicken Curry",
                "Fry the onion with the curry powder and cinnamon.\nAdd the chicken and tomato.\nAdd the potato and simmer for 45 minutes.",
                "chicken|800|g", "onion|2|pcs", "tomato|400|g", "curry powder|2|tbsp", "potato|2|pcs", "cinnamon|1|tsp");

        add(dao, "Morogo and Pap",
                "Cook the maize meal into a stiff pap.\nWash and chop the spinach.\nFry the onion and tomato, add the spinach and steam until soft.",
                "maize meal|300|g", "spinach|500|g", "onion|1|pcs", "tomato|200|g");

        add(dao, "Pap and Sheba",
                "Cook the maize meal into a firm pap.\nFry the onion and chilli in oil.\nAdd the tomato and simmer into a thick sauce and serve over the pap.",
                "maize meal|300|g", "tomato|400|g", "onion|2|pcs", "chilli|1|pcs", "oil|30|ml");

        add(dao, "Peri-Peri Chicken",
                "Blend the chilli, garlic, lemon juice and olive oil into a marinade.\nCoat the chicken and rest for 2 hours.\nBraai or roast until cooked through.",
                "chicken|1|kg", "chilli|4|pcs", "garlic|4|pcs", "lemon|2|pcs", "olive oil|60|ml");

        add(dao, "Mealie Bread",
                "Mix the corn, flour and sugar.\nStir in the eggs, milk and melted butter.\nBake until golden and firm.",
                "corn|400|g", "flour|250|g", "egg|2|pcs", "milk|250|ml", "sugar|50|g", "butter|50|g");

        add(dao, "Buttermilk Rusks",
                "Rub the butter into the flour and sugar.\nMix in the buttermilk and eggs to form a dough.\nBake, cut into fingers and dry slowly in a low oven.",
                "flour|1|kg", "butter|250|g", "buttermilk|500|ml", "sugar|250|g", "egg|2|pcs");

        add(dao, "Queen Cakes",
                "Cream the butter and sugar until pale.\nBeat in the eggs one at a time.\nFold in the flour, baking powder, milk and raisins.\nSpoon into paper cups and bake at 180 degrees for 20 minutes until golden.",
                "butter|125|g", "sugar|125|g", "egg|2|pcs", "flour|125|g", "baking powder|1|tsp", "milk|60|ml", "raisin|50|g");
    }

    // Inserts one recipe, then one row per ingredient string ("name|quantity|unit")
    private static void add(RecipeDao dao, String name, String steps, String... ingredients) {
        Recipe recipe = new Recipe();
        recipe.name = name;
        recipe.steps = steps;
        int recipeId = (int) dao.insertRecipe(recipe);

        for (String s : ingredients) {
            String[] parts = s.split("\\|");
            RecipeIngredient ing = new RecipeIngredient();
            ing.recipeId = recipeId;
            ing.name = parts[0];
            ing.quantity = Double.parseDouble(parts[1]);
            ing.unit = parts[2];
            dao.insertIngredient(ing);
        }
    }
}