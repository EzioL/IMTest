package com.ezio.imtest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity implements RongIM.UserInfoProvider{
    public static String id = "null";
    private final static String token1 =
            "bVYC+nUXMPB/T635bzAgrqzjH8er4YluqSvfxsg4r6Rs7zFtfs2JEPMK4udI6CTd3RF6ZswwJbYQoFoUB2GN9A==";
    private final static String token2 =
            "mrbP4nTp4bZbPMJeWA05HazjH8er4YluqSvfxsg4r6Rs7zFtfs2JEHm6h8o+YralIiedd0SN/fd8xi/LQdlBgA==";
    List<Friend> userIdList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUserInfo();
        userIdList = new ArrayList<>();
        userIdList.add(new Friend("001","春风十里","http://img2.duitang.com/uploads/item/201207/24/20120724123200_x85tj.jpeg"));
        userIdList.add(new Friend("002","超人不会飞","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1671576656,1573074395&fm=116&gp=0.jpg"));
        RongIM.setUserInfoProvider(this,true);
    }

    private void initUserInfo() {

    }

    public void login1(View view) {
        connect(token1);
       /* if (RongIM.getInstance()!=null){
            RongIM.getInstance().startPrivateChat(this,"002","超人不会飞");
        }*/
        startActivity(new Intent(MainActivity.this,HomeActivity.class));
    }

    public void login2(View view) {
        connect(token2);
        /*if (RongIM.getInstance()!=null){
            RongIM.getInstance().startPrivateChat(this,"001","春风十里");
        }*/

        startActivity(new Intent(MainActivity.this,HomeActivity.class));
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


    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : userIdList) {
            if (i.getUserId().equals(s)){
                UserInfo userInfo = new UserInfo(i.getUserId(),i.getNickname(), Uri.parse(i.getPortrait()));
                return userInfo;
            }
        }
        Log.e("getUserInfo: ", "UserId is :"+s);
        return null;
    }
}
