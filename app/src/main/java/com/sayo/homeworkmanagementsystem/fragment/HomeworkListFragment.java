package com.sayo.homeworkmanagementsystem.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

public class HomeworkListFragment extends androidx.fragment.app.Fragment {
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = (String) savedInstanceState.get("userId");

    }
}