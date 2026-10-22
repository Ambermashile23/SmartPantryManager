package com.example.smartpantrymanger;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView tvEmpty, tvExpiryBanner;
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
        tvExpiryBanner = findViewById(R.id.tvExpiryBanner);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // Tapping a row opens the edit screen and passes the item's id via an Intent
        adapter = new PantryAdapter(db.pantryItemDao().getAll(), item -> {
            Intent intent = new Intent(this, AddEditActivity.class);
            intent.putExtra(AddEditActivity.EXTRA_ITEM_ID, item.id);
            startActivity(intent);
        });
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        recyclerPantry.setAdapter(adapter);

        // The + button opens the add screen with no id, which means "add new"
        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditActivity.class)));

        // Bottom navigation: Pantry is this screen; the others open new screens via Intents
        bottomNav.setSelectedItemId(R.id.nav_pantry);
        bottomNav.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_recipes) {
                startActivity(new Intent(this, RecipeActivity.class));
                return false; // keep "Pantry" highlighted
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return false;
            }
            return true;
        });
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

        // Show the expiring-soon banner only if the setting is switched on
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS, MODE_PRIVATE);
        boolean alertsOn = prefs.getBoolean(SettingsActivity.KEY_EXPIRY_ALERTS, true);
        int expiring = alertsOn ? countExpiringSoon(items) : 0;
        if (expiring > 0) {
            tvExpiryBanner.setText(expiring + " item(s) expired or expiring within 3 days");
            tvExpiryBanner.setVisibility(View.VISIBLE);
        } else {
            tvExpiryBanner.setVisibility(View.GONE);
        }
    }

    // Counts items whose expiry date (yyyy-mm-dd) is within 3 days or already past
    private int countExpiringSoon(List<PantryItem> items) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        long threeDays = 3L * 24 * 60 * 60 * 1000;
        long now = System.currentTimeMillis();
        int count = 0;
        for (PantryItem item : items) {
            if (item.expiryDate == null || item.expiryDate.isEmpty()) continue;
            try {
                Date expiry = format.parse(item.expiryDate);
                if (expiry != null && expiry.getTime() - now <= threeDays) {
                    count++;
                }
            } catch (ParseException e) {
                // ignore badly formatted dates
            }
        }
        return count;
    }
}