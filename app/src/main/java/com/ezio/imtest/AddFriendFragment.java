package com.ezio.imtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import io.rong.imkit.RongIM;
import okhttp3.Call;

/**
 *
 * Created by Ezio on 2016/5/17.
 */
public class AddFriendFragment extends Fragment {

    public static final String TAG = "AddFriendFragment";
    String addFriendUrl = "http://10.201.1.185:8080/IMTest/Request_Friend";


    public  static AddFriendFragment instance = null;

    public static AddFriendFragment getInstance() {
        if (instance == null){
            instance = new AddFriendFragment();
        }
        return instance;
    }
    private View mView;
    private Button mButton;
    private EditText mEditText;
    private EditText mEditTextMessage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.friend_add_fragment,null);
        mButton = (Button) mView.findViewById(R.id.friend_add_bt);
        mEditText = (EditText) mView.findViewById(R.id.friend_add_et);
        mEditTextMessage = (EditText) mView.findViewById(R.id.friend_add_message);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = App.sourceUserId;
                String targetUserId = mEditText.getText().toString();
                String message = mEditTextMessage.getText().toString();
                if (targetUserId != null){
                    requestFriend(id,targetUserId,message);
                }else {
                    Toast.makeText(getActivity(), "账号不能空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return mView;
    }

    private void requestFriend(String sourceUserId, String targetUserId, String message) {
        OkHttpUtils
                .get()
                .url(addFriendUrl)
                .addParams("sourceUserId",sourceUserId)
                .addParams("targetUserId",targetUserId)
                .addParams("message",message)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "请求发送成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
