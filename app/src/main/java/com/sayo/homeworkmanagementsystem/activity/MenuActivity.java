package com.sayo.homeworkmanagementsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.sayo.homeworkmanagementsystem.fragment.HomeworkItemFragment;
import com.sayo.homeworkmanagementsystem.R;
import com.sayo.homeworkmanagementsystem.fragment.HomeFragment;
import com.sayo.homeworkmanagementsystem.fragment.SubmittedFragment;
import com.sayo.homeworkmanagementsystem.fragment.UserFragment;
import com.sayo.homeworkmanagementsystem.utils.JSONUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private ViewPager viewPager;

    private LinearLayout homeLayout;
    private LinearLayout homeworkListLayout;
    private LinearLayout submittedLayout;
    private LinearLayout userLayout;

    private TextView homeTextView;
    private TextView homeworkListTextView;
    private TextView submittedTextView;
    private TextView userTextView;

    private ImageView homeImgView;
    private ImageView homeworkListImgView;
    private ImageView submittedImgView;
    private ImageView userImgView;

    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        viewPager = findViewById(R.id.view_pager);

        homeLayout = findViewById(R.id.home_layout);
        homeworkListLayout = findViewById(R.id.homework_list_layout);
        userLayout = findViewById(R.id.user_layout);
        submittedLayout = findViewById(R.id.submitted_layout);

        homeTextView = findViewById(R.id.home_text_view);
        homeworkListTextView = findViewById(R.id.homework_list_text_view);
        submittedTextView = findViewById(R.id.submitted_text_view);
        userTextView = findViewById(R.id.user_text_view);

        homeImgView = findViewById(R.id.home_img_view);
        homeworkListImgView = findViewById(R.id.homework_list_img_view);
        submittedImgView = findViewById(R.id.submitted_img_view);
        userImgView = findViewById(R.id.user_img);

        fragments.add(new HomeFragment());
        fragments.add(new HomeworkItemFragment());
        fragments.add(new SubmittedFragment());
        fragments.add(new UserFragment());

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);

        String username1 = sharedPreferences.getString("username", null);
        Intent intent = getIntent();
        // String username = intent.getStringExtra("username");
        // String phone = intent.getStringExtra("phone");

        // intent.getStringExtra("msg");
        Bundle bundle = new Bundle();
        String userId = "17002";
        bundle.putString("userId", userId);
        fragments.get(1).setArguments(bundle); // submitted fragment
        fragments.get(2).setArguments(bundle);
        fragments.get(3).setArguments(bundle); // user fragment
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        homeworkListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                // String userId = intent.getStringExtra("userId");
                // setContentView(R.layout.activity_menu);
            }
        });

        submittedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int normalColor = Color.parseColor("#ffffff"); // 白色
                int clickedColor = Color.parseColor("#0094fe"); // 蓝色
                switch (position) {
                    case 0:
                        homeTextView.setTextColor(clickedColor); // 0
                        homeworkListTextView.setTextColor(normalColor);
                        submittedTextView.setTextColor(normalColor);
                        userTextView.setTextColor(normalColor);

                        homeImgView.setImageResource(R.drawable.home_clicked); // 0
                        homeworkListImgView.setImageResource(R.drawable.homework_list);
                        submittedImgView.setImageResource(R.drawable.submitted);
                        userImgView.setImageResource(R.drawable.user);
                        break;
                    case 1:
                        homeTextView.setTextColor(normalColor);
                        homeworkListTextView.setTextColor(clickedColor); // 1
                        submittedTextView.setTextColor(normalColor);
                        userTextView.setTextColor(normalColor);

                        homeImgView.setImageResource(R.drawable.home);
                        homeworkListImgView.setImageResource(R.drawable.homework_list_clicked); // 1
                        submittedImgView.setImageResource(R.drawable.submitted);
                        userImgView.setImageResource(R.drawable.user);

                        break;
                    case 2:
                        homeTextView.setTextColor(normalColor);
                        homeworkListTextView.setTextColor(normalColor);
                        submittedTextView.setTextColor(clickedColor);
                        userTextView.setTextColor(normalColor);

                        homeImgView.setImageResource(R.drawable.home);
                        homeworkListImgView.setImageResource(R.drawable.homework_list);
                        submittedImgView.setImageResource(R.drawable.submitted_clicked);
                        userImgView.setImageResource(R.drawable.user);
                        break;
                    case 3:
                        homeTextView.setTextColor(normalColor);
                        homeworkListTextView.setTextColor(normalColor);
                        submittedTextView.setTextColor(normalColor);
                        userTextView.setTextColor(clickedColor);

                        homeImgView.setImageResource(R.drawable.home);
                        homeworkListImgView.setImageResource(R.drawable.homework_list);
                        submittedImgView.setImageResource(R.drawable.submitted);
                        userImgView.setImageResource(R.drawable.user_clicked);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}