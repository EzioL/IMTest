package event;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sea_monster.network.ApiCallback;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;

/**
 * Created by Ezio on 2016/5/21.
 */
public final class TestEvent implements RongIMClient.OnReceiveMessageListener,RongIM.OnSendMessageListener {

    private static TestEvent mRongCloudInstance;
    private Context mContext;
    private Handler mHandler;

    /**
     * 构造方法。
     *
     * @param context 上下文。
     */
    private TestEvent(Context context) {
        mContext = context;
        initDefaultListener();
        mHandler = new Handler();
    }

    private void initDefaultListener() {
        RongIM.getInstance().setSendMessageListener(this);//设置发出消息接收监听器.
        RongIM.getInstance().getRongIMClient().setOnReceiveMessageListener(this);//设置消息接收监听器。
    }


    /**
     * 接收消息的监听器：OnReceiveMessageListener 的回调方法，接收到消息后执行。
     * @param message 接收到的消息的实体信息。
     * @param i  剩余未拉取消息数目。
     *
     */
    @Override
    public boolean onReceived(Message message, int i) {
        MessageContent messageContent = message.getContent();
        if (messageContent instanceof TextMessage) {//文本消息
            TextMessage textMessage = (TextMessage) messageContent;
            Log.d("test123", "onReceived-TextMessage:" + textMessage.getContent());
        }if (messageContent instanceof ContactNotificationMessage) {//好友添加消息
            ContactNotificationMessage contactContentMessage = (ContactNotificationMessage) messageContent;
            Log.d("test123", "onReceived-ContactNotificationMessage:getExtra;" + contactContentMessage.getExtra());
            Log.d("test123", "onReceived-ContactNotificationMessage:+getmessage:" + contactContentMessage.getMessage().toString());
        }
        return false;
    }







    @Override
    public Message onSend(Message message) {

        return null;
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        return false;
    }
}
