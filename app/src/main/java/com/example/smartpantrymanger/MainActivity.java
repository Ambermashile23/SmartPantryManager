package com.example.smartpantrymanger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView tvEmpty;
    private PantryAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);
        RecipeSeeder.seedIfEmpty(db);
        recyclerPantry = findViewById(R.id.recyclerPantry);
        tvEmpty = findViewById(R.id.tvEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        // Tapping a row opens the edit screen and passes the item's id via an Intent
        adapter = new PantryAdapter(db.pantryItemDao().getAll(), item -> {
            Intent intent = new Intent(this, AddEditActivity.class);
            intent.putExtra(AddEditActivity.EXTRA_ITEM_ID, item.id);
            startActivity(intent);
        });
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        recyclerPantry.setAdapter(adapter);

        // The + button opens the same screen with no id, which means "add new"
        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditActivity.class)));
        findViewById(R.id.btnRecipes).setOnClickListener(v ->
                startActivity(new Intent(this, RecipeActivity.class)));
    }

    // Runs every time the screen comes back into view, so the list stays fresh
    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        List<PantryItem> items = db.pantryItemDao().getAll();
        adapter.setItems(items);
        tvEmpty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }
}