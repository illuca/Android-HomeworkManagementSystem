package com.sayo.homeworkmanagementsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sayo.homeworkmanagementsystem.R;
import com.sayo.homeworkmanagementsystem.utils.NetworkAPI;
import com.sayo.homeworkmanagementsystem.activity.XiangQingActivity;
import com.sayo.homeworkmanagementsystem.bean.Page;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HomeFragment extends Fragment {
    private ListView mlistView;
    private BaseAdapter adapter;
    private TextView tvShangye;
    private TextView tvNext;
    private EditText edtYeMa;
    private Button btnTiaozhuan;
    private int page = 1;
    private TextView tvCurrentPage;
    private int totalPage;
    private TextView bookName;
    private Button searchBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,  savedInstanceState);
        mlistView = view.findViewById(R.id.menu_list);
        tvNext = view.findViewById(R.id.tv_next);
        tvShangye = view.findViewById(R.id.tv_shangye);
        tvCurrentPage = view.findViewById(R.id.tv_currentPage);
        edtYeMa = view.findViewById(R.id.edt_yema);
        btnTiaozhuan = view.findViewById(R.id.btn_tiaozhuan);
        bookName = view.findViewById(R.id.edt_bookName);
        searchBtn = view.findViewById(R.id.search_btn);

        String bookNameStr = bookName.getText().toString().trim();
    }
}