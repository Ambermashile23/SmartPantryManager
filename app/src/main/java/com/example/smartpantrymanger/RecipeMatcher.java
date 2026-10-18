package com.example.smartpantrymanger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    private static final double EPSILON = 0.0001;

    // Returns only the recipes whose EVERY ingredient is in the pantry in enough quantity
    public static List<Recipe> getSuggestedRecipes(AppDatabase db) {
        List<PantryItem> pantry = db.pantryItemDao().getAll();
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : db.recipeDao().getAllRecipes()) {
            List<RecipeIngredient> needed = db.recipeDao().getIngredientsForRecipe(r.id);
            if (canMake(needed, pantry)) {
                result.add(r);
            }
        }
        return result;
    }

    // STRICT RULE: if even one ingredient is missing or short, the recipe is rejected
    public static boolean canMake(List<RecipeIngredient> needed, List<PantryItem> pantry) {
        if (needed.isEmpty()) return false;
        for (RecipeIngredient req : needed) {
            if (!hasEnough(req, pantry)) {
                return false;
            }
        }
        return true;
    }

    // Adds up every pantry entry with the same (normalised) name and a compatible unit
    private static boolean hasEnough(RecipeIngredient req, List<PantryItem> pantry) {
        String reqName = normaliseName(req.name);
        String reqUnit = cleanUnit(req.unit);
        double required = toBase(req.quantity, reqUnit);
        double available = 0;

        for (PantryItem p : pantry) {
            if (!normaliseName(p.name).equals(reqName)) continue;
            String pUnit = cleanUnit(p.unit);
            if (!isMeasure(pUnit) == isMeasure(reqUnit)) continue; // can't compare pcs with g/ml
            available += toBase(p.quantity, pUnit);
        }
        return available + EPSILON >= required;
    }

    // Lowercase, trim and reduce simple plurals so "Tomatoes" matches "tomato"
    public static String normaliseName(String raw) {
        if (raw == null) return "";
        String[] words = raw.trim().toLowerCase(Locale.ROOT).split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(singular(words[i]));
        }
        return sb.toString();
    }

    private static String singular(String w) {
        if (w.length() > 4 && w.endsWith("ies")) w = w.substring(0, w.length() - 3) + "y";
        else if (w.length() > 4 && w.endsWith("oes")) w = w.substring(0, w.length() - 2);
        else if (w.length() > 3 && w.endsWith("s") && !w.endsWith("ss")) w = w.substring(0, w.length() - 1);
        // so "chilli" and "chillies" end up identical
        if (w.endsWith("y")) w = w.substring(0, w.length() - 1) + "i";
        return w;
    }

    private static String cleanUnit(String unit) {
        return unit == null ? "pcs" : unit.trim().toLowerCase(Locale.ROOT);
    }

    // g/kg/ml/l/cup/tbsp/tsp are treated as measures (1 g is roughly 1 ml); pcs are counted
    private static boolean isMeasure(String unit) {
        return !unit.equals("pcs");
    }

    // Converts to grams/millilitres (or a plain count for pcs)
    private static double toBase(double qty, String unit) {
        switch (unit) {
            case "kg":
            case "l":
                return qty * 1000;
            case "cup":
                return qty * 250;
            case "tbsp":
                return qty * 15;
            case "tsp":
                return qty * 5;
            default: // g, ml, pcs
                return qty;
        }
    }
}