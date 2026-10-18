package com.example.smartpantrymanger;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        AppDatabase db = AppDatabase.getInstance(this);
        int id = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);
        Recipe selected = db.recipeDao().getRecipeById(id);
        if (selected == null) {
            finish();
            return;
        }

        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvSteps = findViewById(R.id.tvDetailSteps);

        tvName.setText(selected.name);

        // Full ingredient list
        StringBuilder ingredientText = new StringBuilder();
        List<RecipeIngredient> ingredients = db.recipeDao().getIngredientsForRecipe(id);
        for (RecipeIngredient ing : ingredients) {
            ingredientText.append("• ").append(ing.name)
                    .append(" - ").append(formatQuantity(ing.quantity))
                    .append(" ").append(ing.unit).append("\n");
        }
        tvIngredients.setText(ingredientText.toString().trim());

        // Numbered method steps
        String[] steps = selected.steps.split("\n");
        StringBuilder stepText = new StringBuilder();
        for (int i = 0; i < steps.length; i++) {
            stepText.append(i + 1).append(". ").append(steps[i]).append("\n\n");
        }
        tvSteps.setText(stepText.toString().trim());
    }

    // Shows 500 instead of 500.0
    private String formatQuantity(double q) {
        if (q == Math.rint(q)) return String.valueOf((long) q);
        return String.valueOf(q);
    }
}