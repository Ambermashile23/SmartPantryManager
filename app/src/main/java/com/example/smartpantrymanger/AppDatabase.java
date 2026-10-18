package com.example.smartpantrymanger;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PantryItem.class, Recipe.class, RecipeIngredient.class},
        version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PantryItemDao pantryItemDao();
    public abstract RecipeDao recipeDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "smart_pantry_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}