package com.sayo.homeworkmanagementsystem.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJSON extends JSONObject {
    @NonNull
    @NotNull
    @Override
    public JSONObject put(@NonNull @NotNull String name, @Nullable @org.jetbrains.annotations.Nullable Object value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
}