package com.example.smartpantrymanger;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "item_id";

    private final String[] units = {"g", "kg", "ml", "l", "cup", "tbsp", "tsp", "pcs"};

    private EditText etName, etQuantity, etExpiry;
    private Spinner spinnerUnit;
    private Button btnSave, btnDelete;
    private AppDatabase db;
    private PantryItem editingItem = null; // null means we are adding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + 32, bars.top + 32, bars.right + 32, bars.bottom + 32);
            return insets;
        });

        db = AppDatabase.getInstance(this);
        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etExpiry = findViewById(R.id.etExpiry);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        TextView tvTitle = findViewById(R.id.tvTitle);

        spinnerUnit.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, units));

        // Tap the expiry field to pick a date from a calendar; long-press to clear it
        etExpiry.setOnClickListener(v -> showDatePicker());
        etExpiry.setOnLongClickListener(v -> {
            etExpiry.setText("");
            Toast.makeText(this, "Expiry date cleared", Toast.LENGTH_SHORT).show();
            return true;
        });

        // If an id was passed in the Intent, we are editing an existing item
        int id = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
        if (id != -1) {
            editingItem = db.pantryItemDao().getById(id);
            if (editingItem != null) {
                tvTitle.setText("Edit Ingredient");
                etName.setText(editingItem.name);
                etQuantity.setText(String.valueOf(editingItem.quantity));
                etExpiry.setText(editingItem.expiryDate);
                for (int i = 0; i < units.length; i++) {
                    if (units[i].equals(editingItem.unit)) {
                        spinnerUnit.setSelection(i);
                    }
                }
                btnDelete.setVisibility(View.VISIBLE);
            }
        }

        btnSave.setOnClickListener(v -> saveItem());
        btnDelete.setOnClickListener(v -> confirmDelete());
    }

    // Opens a calendar dialog and writes the chosen date as yyyy-mm-dd
    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // If a date is already set, open the calendar on that date
        String current = etExpiry.getText().toString();
        if (current.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] parts = current.split("-");
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]) - 1;
            day = Integer.parseInt(parts[2]);
        }

        new DatePickerDialog(this, (view, y, m, d) -> {
            etExpiry.setText(String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d));
            etExpiry.setError(null);
        }, year, month, day).show();
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String qtyText = etQuantity.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        // Input validation
        if (name.isEmpty()) {
            etName.setError("Name is required");
            return;
        }
        double qty;
        try {
            qty = Double.parseDouble(qtyText);
        } catch (NumberFormatException e) {
            etQuantity.setError("Enter a valid number");
            return;
        }
        if (qty <= 0) {
            etQuantity.setError("Quantity must be more than 0");
            return;
        }
        if (!expiry.isEmpty() && !expiry.matches("\\d{4}-\\d{2}-\\d{2}")) {
            etExpiry.setError("Use the format yyyy-mm-dd");
            return;
        }

        String unit = units[spinnerUnit.getSelectedItemPosition()];

        if (editingItem == null) {
            PantryItem item = new PantryItem();
            item.name = name;
            item.quantity = qty;
            item.unit = unit;
            item.expiryDate = expiry;
            db.pantryItemDao().insert(item);        // Create
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {
            editingItem.name = name;
            editingItem.quantity = qty;
            editingItem.unit = unit;
            editingItem.expiryDate = expiry;
            db.pantryItemDao().update(editingItem); // Update
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish(); // go back to the list
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient?")
                .setMessage("Remove " + editingItem.name + " from your pantry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.pantryItemDao().delete(editingItem); // Delete
                    Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}