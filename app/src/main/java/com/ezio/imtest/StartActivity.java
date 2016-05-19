package com.ezio.imtest;

import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;

public class StartActivity extends AppCompatActivity implements RongIM.UserInfoProvider {
    private EditText userId;
    private EditText password;
    String token;
    String test = "JQaMtKv6gQNpnJmj8ZwikCPwvh9fGjOCCQyxHfOT2K+9GwQv+mkeQhV8xyGKBfyk7BQnHGQeCzdAWlzNBGFDjw==";

    String loginurl = "http://10.40.7.21:8080/IMTest/LoginServlet";
    String regurl = "http://10.40.7.21:8080/IMTest/RegServlet";
    String username;
    String pass;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initViews();
        //connect(test);


        RongIM.setUserInfoProvider(this, true);
    }

    private void initViews() {
        userId = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.pass);
    }

    public void login(View view) {
        username = userId.getText().toString();
        pass = password.getText().toString();
        getLogin(username, pass);
    }

    private void getLogin(String username, String pass) {
        OkHttpUtils
                .get()
                .url(loginurl)
                .addParams("username", username)
                .addParams("password", pass)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("false")) {
                            Toast.makeText(StartActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("12345: ", response);
                            data = new Gson().fromJson(response, Data.class);
                            token = data.getToken();
                            Log.e("12345: ", token);
                            connect(token);


                        }

                    }
                });
    }

    public void registered(View view) {
        username = userId.getText().toString();
        pass = password.getText().toString();
        getToken(username, pass);
    }

    private void getToken(String username, String password) {
        OkHttpUtils
                .get()
                .url(regurl)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("false")) {
                            Toast.makeText(StartActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("12345: ", response);
                            data = new Gson().fromJson(response, Data.class);
                            token = data.getToken();
                            Log.e("12345: ", token);
                            connect(token);

                        }
                    }
                });
    }

    //融云的连接
    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("12345: ", "过期");
            }

            @Override
            public void onSuccess(String s) {
                Log.e("12345: ", "成功");
                startActivity(new Intent(StartActivity.this, HomeActivity.class));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("12345: ", errorCode.toString());
            }
        });
    }


    @Override
    public UserInfo getUserInfo(String s) {

        UserInfo userInfo = new UserInfo(data.getUserId(), "run"+data.getUserId(),
                Uri.parse("http://img2.duitang.com/uploads/item/201207/24/20120724123200_x85tj.jpeg"));

        return userInfo;


    }
}
