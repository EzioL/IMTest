package com.ezio.imtest;



import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import event.TestEvent;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.TextMessage;

public class HomeActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    private List<Fragment> mFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //获取融云会话列表的对象
        mConversationList = initConversationList();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFragment.add(HomeFragment.getInstance());
        mFragment.add(mConversationList);
        mFragment.add(FriendFragment.getInstance());
        mFragment.add(AddFriendFragment.getInstance());
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }
            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);


        //设置消息接收监听器。
        RongIM.getInstance().getRongIMClient().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                MessageContent messageContent = message.getContent();
                if (messageContent instanceof TextMessage) {//文本消息
                    TextMessage textMessage = (TextMessage) messageContent;
                    Log.d("test123", "onReceived-TextMessage:" + textMessage.getContent());
                }if (messageContent instanceof ContactNotificationMessage) {//好友添加消息
                    //应该发送一个广播
                    ContactNotificationMessage contactContentMessage = (ContactNotificationMessage) messageContent;

                    Log.d("test123", "onReceived-ContactNotificationMessage:id;" + contactContentMessage.getSourceUserId());
                    Log.d("test123", "onReceived-ContactNotificationMessage:+getmessage:" + contactContentMessage.getMessage().toString());

                    Intent in = new Intent();
                    in.setAction("addFriend");
                    in.putExtra("TestAdd", contactContentMessage);
                    in.putExtra("has_message", true);
                    HomeActivity.this.sendBroadcast(in);

                    Log.d("test123", "广播发送成功了？");

                }
                return false;
            }
        });
    }



    private Fragment initConversationList() {
        Fragment fragment = null;
        if (mConversationFragment ==  null){
            ConversationListFragment listFragment = ConversationListFragment.getInstance();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                    .build();
            listFragment.setUri(uri);
            fragment = listFragment;
        }else {
           fragment = mConversationFragment;
        }
        return fragment;
    }
}
