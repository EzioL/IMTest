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

import beans.User;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;

public class StartActivity extends AppCompatActivity implements RongIM.UserInfoProvider {
    private EditText userId;
    private EditText password;
    String token;
    String test = "JQaMtKv6gQNpnJmj8ZwikCPwvh9fGjOCCQyxHfOT2K+9GwQv+mkeQhV8xyGKBfyk7BQnHGQeCzdAWlzNBGFDjw==";
    String loginurl = "http://10.201.1.185:8080/IMTest/LoginServlet";
    String regurl = "http://10.201.1.185:8080/IMTest/RegServlet";
    String userid;
    String pass;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initViews();
        RongIM.setUserInfoProvider(this, true);




    }

    private void initViews() {
        userId = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.pass);
    }

    public void login(View view) {
        userid = userId.getText().toString();
        pass = password.getText().toString();
        getLogin(userid, pass);
    }

    private void getLogin(String userid, String pass) {
        //设置偏好设置或者从数据库中验证
        //这里m欸次都从数据库中获取验证
        request(loginurl,userid, pass);
    }

    public void registered(View view) {
        userid = userId.getText().toString();
        pass = password.getText().toString();
        getToken(userid, pass);
    }

    private void getToken(String userid, String password) {

        request(regurl,userid, pass);
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
                App.sourceUserId = s;
                startActivity(new Intent(StartActivity.this, HomeActivity.class));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("12345: ", errorCode.toString());
            }
        });
    }

    private void request(String url,String userid, String pass) {
        OkHttpUtils
                .get()
                .url(url)
                .addParams("userid", userid)
                .addParams("password", pass)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("login"+false)) {
                            Toast.makeText(StartActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }else if (response.equals("reg"+false)){
                            Toast.makeText(StartActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("12345: ", response);
                            user = new Gson().fromJson(response, User.class);
                            token = user.getToken();
                            Log.e("12345: ", token);
                            connect(token);
                        }

                    }
                });
    }
    @Override
    public UserInfo getUserInfo(String s) {

        UserInfo userInfo = new UserInfo(user.getUserId(), user.getUserName(),
                Uri.parse(user.getPortraitUri()));
        RongIM.getInstance().setCurrentUserInfo(userInfo);
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        return userInfo;

    }
}
