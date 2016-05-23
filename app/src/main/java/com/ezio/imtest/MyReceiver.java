package com.ezio.imtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import io.rong.message.ContactNotificationMessage;

/**
 * Created by Ezio on 2016/5/23.
 */
public class MyReceiver extends BroadcastReceiver {
    View mView;
    @Override
    public void onReceive(Context context, Intent intent) {
       if (intent.getAction().equals("addFriend")){
           ContactNotificationMessage contactContentMessage =
                    (ContactNotificationMessage) intent.getExtras().get("TestAdd");
           Log.e("test123","接收"+contactContentMessage.getSourceUserId() );
           intent.getExtras().getString("has_message");
           Log.e("test123", "ok" );

        }
    }


}
