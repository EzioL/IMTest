package api;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.sea_monster.network.AbstractHttpRequest;
import com.sea_monster.network.ApiCallback;
import com.sea_monster.network.ApiReqeust;
import com.sea_monster.network.BaseApi;
import com.sea_monster.network.NetworkManager;

import java.net.URI;
import java.util.ArrayList;

import model.Status;
/**
 * Created by Ezio on 2016/5/18.
 */
public class TestApi extends BaseApi {
    private static String HOST = "http://10.40.7.21:8080/IMTest/";
    private final static String LOGIN = "LoginServlet";
    private final static String REG = "RegServlet";
    //private final static String TOKEN = "token";


    private Handler mWorkHandler;
    private HandlerThread mWorkThread;
    static Handler mHandler;

    public TestApi(Context context) {
        super(NetworkManager.getInstance(), context);
        mHandler = new Handler(Looper.getMainLooper());
        mWorkThread = new HandlerThread("DemoApi");
        mWorkThread.start();
        mWorkHandler = new Handler(mWorkThread.getLooper());
    }



}
