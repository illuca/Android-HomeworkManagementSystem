package com.sayo.homeworkmanagementsystem.utils;

import android.util.Log;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Network {
    public static String SERVER_ADDRESS = "http://192.168.8.118:9090";

    public static Response login(String userId, String password, int userType) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("password", password);
            jsonObject.put("userType", userType);

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

            String url = Network.SERVER_ADDRESS + "/user/login";
            // String url = "https://www.bilibili.com";
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseResponse(String TAG, Response response) {
        // 解析响应
        String responseData = null;
        try {
            responseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 只在debug时才打印,不会影响正常运行时的性能
        Log.e(TAG, "onResponse: " + responseData);
        try {
            JSONObject responseJsonObject = new JSONObject(responseData);
            return (String) responseJsonObject.get("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}