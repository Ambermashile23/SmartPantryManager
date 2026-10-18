package com.example.smartpantrymanger;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        // For now this lists every recipe. Next we filter it with the strict-matching rule.
        List<Recipe> recipeList = db.recipeDao().getAllRecipes();
        tvTitle.setText("Recipes (" + recipeList.size() + ")");

        RecipeAdapter adapter = new RecipeAdapter(recipeList, recipe ->
                Toast.makeText(this, recipe.name, Toast.LENGTH_SHORT).show());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        tvNoRecipes.setVisibility(recipeList.isEmpty() ? View.VISIBLE : View.GONE);
    }
}