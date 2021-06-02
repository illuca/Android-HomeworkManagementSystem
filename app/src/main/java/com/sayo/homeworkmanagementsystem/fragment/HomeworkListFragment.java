package com.sayo.homeworkmanagementsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sayo.homeworkmanagementsystem.R;
import com.sayo.homeworkmanagementsystem.utils.MyJSON;
import com.sayo.homeworkmanagementsystem.utils.Network;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeworkListFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    private int pageCount;
    private int pageIndex = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // String userId = (String) savedInstanceState.get("userId");
        Intent intent = new Intent();
        // setContentView(R.layout.activity_menu);

        MyJSON queryForm = new MyJSON();
        queryForm.put("homeworkId", "");
        queryForm.put("homeTitle", "");
        query(queryForm);
    }

    public void query(MyJSON queryForm) {
        String url = Network.SERVER_ADDRESS + "/page/count";
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType, queryForm.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    System.out.println(responseBody.string());
                    // pageCount = response;
                    pageIndex = 1;
                    getPage(pageIndex);
                }
            }
        });
    }

    public void getPage(int index) {

    }
}