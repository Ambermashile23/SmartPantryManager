package com.example.smartpantrymanger;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantry_items")
public class PantryItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double quantity;
    public String unit;
    public String expiryDate; // optional, can be null
}