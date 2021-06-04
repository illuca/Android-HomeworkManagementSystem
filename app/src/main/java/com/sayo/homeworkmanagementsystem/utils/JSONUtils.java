package com.sayo.homeworkmanagementsystem.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils extends JSONObject {
    public JSONUtils() {
    }

    public static JSONObject newJSON(String value) {
        try {
            return new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject put(JSONObject jsonObject, @NonNull @NotNull String name, @Nullable @org.jetbrains.annotations.Nullable Object value) {
        try {
            return jsonObject.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Object get(JSONObject resultVO, String key) {
        Object o = null;
        try {
            o = resultVO.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }


}