package com.ezio.imtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.rong.imkit.RongIM;

/**
 *
 * Created by Ezio on 2016/5/17.
 */
public class FriendFragment extends Fragment {

    public  static FriendFragment instance = null;
    public static FriendFragment getInstance() {
        if (instance == null){
            instance = new FriendFragment();
        }
        return instance;
    }
    private View mView;
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.friend_fragment,null);
        mButton = (Button) mView.findViewById(R.id.friend);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance()!=null){
                    RongIM.getInstance().startPrivateChat(getActivity(),"002","私人聊天");
                }
            }
        });

        return mView;
    }
}
