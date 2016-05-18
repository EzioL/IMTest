package com.ezio.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
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

                            Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("12345: ", response);
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

                            Toast.makeText(MainActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("12345: ", response);
                            Data data = new Gson().fromJson(response,Data.class);
                            token = data.getToken();
                        }

                    }
                });
    }
}
