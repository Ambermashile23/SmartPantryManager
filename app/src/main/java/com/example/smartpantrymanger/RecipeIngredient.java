package com.example.smartpantrymanger;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_ingredients")
public class RecipeIngredient {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public String name;
    public double quantity;
    public String unit;
}