package com.example.entities;

import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;

public class CustomConverters {
    @TypeConverter
    public List<String> fromString(String value) {
        return Arrays.asList(value.split(","));
    }

    @TypeConverter
    public String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : list) {
            stringBuilder.append(item).append(",");
        }
        // Remove the last comma
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}

