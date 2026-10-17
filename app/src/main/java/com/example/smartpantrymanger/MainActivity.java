package com.example.smartpantrymanger;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        recyclerPantry = findViewById(R.id.recyclerPantry);
        tvEmpty = findViewById(R.id.tvEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        adapter = new PantryAdapter(db.pantryItemDao().getAll(), item ->
                Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show());
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        recyclerPantry.setAdapter(adapter);

        fabAdd.setOnClickListener(v ->
                Toast.makeText(this, "Add screen coming next", Toast.LENGTH_SHORT).show());
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