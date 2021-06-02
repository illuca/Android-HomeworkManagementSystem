package com.sayo.homeworkmanagementsystem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.sayo.homeworkmanagementsystem.R;
import com.sayo.homeworkmanagementsystem.utils.Config;
import com.sayo.homeworkmanagementsystem.utils.Md5;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private EditText loginUserId;
    private EditText login_password;
    private Button btn_login;
    private Button btnRegister;
    private RadioGroup radioGroup;
    private SharedPreferences sharedPreferences;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // 可以把重要的值放入, 因为手机方向转变会导致变量值丢失
        super.onCreate(savedInstanceState);
        // 设置显示内容
        setContentView(R.layout.activity_login);
        btnRegister = findViewById(R.id.register_btn);
        loginUserId = findViewById(R.id.login_user_id_edit_text);
        login_password = findViewById(R.id.login_password_edit_text);
        btn_login = findViewById(R.id.login_btn);
        radioGroup = findViewById(R.id.login_radio_group);
        RadioButton loginStudentRadioBtn = findViewById(R.id.login_student_radio_btn);

        // 测试用例: 学生
        final String _userId = "17002";
        String _password = "123";
        loginUserId.setText(_userId);
        login_password.setText(Md5.md5(_password + Md5.md5(_userId)));
        loginStudentRadioBtn.setChecked(true);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取用户身份
                        RadioButton loginStudent = findViewById(R.id.login_student_radio_btn);
                        loginStudent.setChecked(true);
                        int userType = getUserType();

                        Response response = login(_userId, login_password.getText().toString(), userType);
                        if (response == null) {
                            // 访问服务器失败
                            Log.d("error", "网络连接失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // 访问服务器成功
                            final String message = (String) parseResponse(response);
                            if ("success".equals(message)) {
                                // 登录成功
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Intent intent = new Intent(LoginActivity.this, com.sayo.homeworkmanagementsystem.activity.MenuActivity.class);
                                intent.putExtra("userId", loginUserId.getText().toString());
                                intent.putExtra("username", "123");
                                intent.putExtra("phone", "15223423423");
                                startActivity(intent);
                            } else {
                                // 登录失败
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private String parseResponse(Response response) {
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

    public Response login(String userId, String password, int userType) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("password", password);
            jsonObject.put("userType", userType);

            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

            String url = Config.SERVER_ADDRESS + "/user/login";
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

    // 获取用户身份
    int getUserType() {
        RadioButton loginStudent = findViewById(R.id.login_student_radio_btn);
        RadioButton loginTeacher = findViewById(R.id.login_teacher_radio_btn);
        if (loginStudent.isChecked()) {
            return 1;
        } else if (loginTeacher.isChecked()) {
            return 2;
        } else {
            return 0;
        }
    }
}