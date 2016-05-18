package com.ezio.imtest;

import android.content.Intent;
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

public class StartActivity extends AppCompatActivity {
    private EditText userId;
    private EditText password;
    String token;
    String loginurl =  "http://10.40.7.21:8080/IMTest/LoginServlet";
    String regurl = "http://10.40.7.21:8080/IMTest/RegServlet";
    String username;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initViews();
    }
    private void initViews() {
        userId = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.pass);
    }
    public void login(View view) {
        username = userId.getText().toString();
        pass = password.getText().toString();
        getLogin(username,pass);
    }

    private void getLogin(String username, String pass) {
        OkHttpUtils
                .get()
                .url(loginurl)
                .addParams("username",username)
                .addParams("password",pass)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("false")){
                            Toast.makeText(StartActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }else {
                            Data data = new Gson().fromJson(response,Data.class);
                            token = data.getToken();
                            Log.e("12345: ", response);
                            connect(token);
                            startActivity(new Intent(StartActivity.this,HomeActivity.class));

                        }

                    }
                });
    }

    public void registered(View view) {
        username = userId.getText().toString();
        pass = password.getText().toString();
        getToken(username,pass);
    }

    private void getToken(String username,String password) {
        OkHttpUtils
                .get()
                .url(regurl)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("false")){

                            Toast.makeText(StartActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("12345: ", response);
                            Data data = new Gson().fromJson(response,Data.class);
                            token = data.getToken();
                            connect(token);
                            startActivity(new Intent(StartActivity.this,HomeActivity.class));
                        }

                    }
                });
    }

    //融云的连接
    private void connect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                //s 是userId
                // Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    public UserInfo getUserInfo(String s) {
                        return null;
                    }
                },true);


            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
