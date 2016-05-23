package com.ezio.imtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import io.rong.message.ContactNotificationMessage;

public class Main2Activity extends AppCompatActivity{
    BroadcastReceiver mReceiver;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mTextView = (TextView) findViewById(R.id.userId_tv);
        receiver();
    }

    private void receiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("addFriend")){
                    ContactNotificationMessage contactContentMessage =
                            (ContactNotificationMessage) intent.getExtras().get("TestAdd");
                    Log.e("test123","接收"+contactContentMessage.getSourceUserId() );
                    intent.getExtras().getString("has_message");
                    Log.e("test123", "ok" );
                    mTextView.setText(contactContentMessage.getSourceUserId());

                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("addFriend");
        registerReceiver(mReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}