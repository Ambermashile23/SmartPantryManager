package com.example.smartpantrymanger;

public class RecipeSeeder {

    public static void seedIfEmpty(AppDatabase db) {
        RecipeDao dao = db.recipeDao();
        if (dao.countRecipes() > 0) return;

        add(dao, "Scrambled Eggs", "Beat the eggs.\nMelt butter in a pan.\nPour in eggs and stir until set.\nSeason with salt.",
                "egg|3|pcs", "butter|10|g", "salt|1|tsp");
        add(dao, "Cheese Omelette", "Beat the eggs with salt.\nCook in a pan until nearly set.\nAdd cheese, fold and serve.",
                "egg|3|pcs", "cheese|50|g", "salt|1|tsp");
        add(dao, "Tomato Pasta", "Boil the pasta.\nFry onion and garlic.\nAdd chopped tomatoes and simmer.\nMix with pasta and season.",
                "pasta|200|g", "tomato|3|pcs", "onion|1|pcs", "garlic|2|pcs", "salt|1|tsp");
        add(dao, "Egg Fried Rice", "Cook the rice and let it cool.\nScramble the eggs.\nStir-fry grated carrot.\nAdd rice, eggs and soy sauce.",
                "rice|1|cup", "egg|2|pcs", "carrot|1|pcs", "soy sauce|2|tbsp");
        add(dao, "Pancakes", "Mix flour, egg, milk and sugar into a batter.\nFry ladlefuls in a pan until golden on both sides.",
                "flour|1|cup", "egg|1|pcs", "milk|250|ml", "sugar|2|tbsp");
        add(dao, "Cheese Toast", "Butter the bread.\nTop with cheese.\nGrill until melted and golden.",
                "bread|2|pcs", "cheese|40|g", "butter|10|g");
        add(dao, "Mashed Potatoes", "Peel and boil the potatoes until soft.\nDrain and mash with butter and milk.\nSeason with salt.",
                "potato|4|pcs", "butter|30|g", "milk|100|ml", "salt|1|tsp");
        add(dao, "Garlic Bread", "Mix crushed garlic into soft butter.\nSpread on the bread.\nBake until crisp.",
                "bread|4|pcs", "butter|40|g", "garlic|2|pcs");
        add(dao, "Chicken and Rice", "Brown the chicken pieces with onion.\nAdd rice and water.\nCover and cook until the rice is tender.\nSeason with salt.",
                "chicken|300|g", "rice|1|cup", "onion|1|pcs", "salt|1|tsp");
        add(dao, "Vegetable Soup", "Chop all vegetables.\nSimmer in water until soft.\nSeason with salt and serve.",
                "carrot|2|pcs", "potato|2|pcs", "onion|1|pcs", "salt|1|tsp");
        add(dao, "Tuna Sandwich", "Mix the tuna with mayonnaise.\nSpread on bread and close the sandwich.",
                "tuna|1|pcs", "bread|2|pcs", "mayonnaise|2|tbsp");
        add(dao, "Banana Pancakes", "Mash the bananas.\nMix with eggs and flour.\nFry spoonfuls until golden on both sides.",
                "banana|2|pcs", "egg|2|pcs", "flour|0.5|cup");
        add(dao, "French Toast", "Whisk eggs, milk and sugar.\nSoak the bread slices.\nFry until golden on both sides.",
                "bread|4|pcs", "egg|2|pcs", "milk|100|ml", "sugar|1|tbsp");
        add(dao, "Rice Pudding", "Simmer rice in milk, stirring often.\nAdd sugar and cook until thick and creamy.",
                "rice|1|cup", "milk|500|ml", "sugar|3|tbsp");
        add(dao, "Tomato and Egg Stir-fry", "Heat oil in a pan.\nCook chopped tomatoes until soft.\nPour in beaten eggs and stir until set.\nSeason with salt.",
                "tomato|3|pcs", "egg|3|pcs", "salt|1|tsp", "oil|1|tbsp");
        add(dao, "Potato Fries", "Cut potatoes into strips.\nFry in hot oil until golden.\nDrain and sprinkle with salt.",
                "potato|3|pcs", "oil|3|tbsp", "salt|1|tsp");
    }

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