package com.example.smartpantrymanger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        AppDatabase db = AppDatabase.getInstance(this);
        RecyclerView recycler = findViewById(R.id.recyclerRecipes);
        TextView tvNoRecipes = findViewById(R.id.tvNoRecipes);
        TextView tvTitle = findViewById(R.id.tvRecipesTitle);

        // Only recipes that pass the strict-matching rule
        List<Recipe> suggested = RecipeMatcher.getSuggestedRecipes(db);
        tvTitle.setText("Suggested Recipes (" + suggested.size() + ")");

        RecipeAdapter adapter = new RecipeAdapter(suggested, recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id);
            startActivity(intent);
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        tvNoRecipes.setVisibility(suggested.isEmpty() ? View.VISIBLE : View.GONE);
    }
}