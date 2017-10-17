package com.sp.shangpin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sp.shangpin.entity.SystemInfo;
import com.sp.shangpin.entity.UserInfo;
import com.sp.shangpin.utils.AppUtil;
import com.sp.shangpin.utils.CrashHandler;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.FileUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/7.
 */

public class MyApplication extends Application {

    public static SystemInfo systemInfo;//系统信息
    public static UserInfo userInfo;//用户信息
    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    public static void deleteActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public static void cleanAllActivitys() {
        for (int i = 0; i < activityList.size(); i++) {
            if (null != activityList.get(i) && !activityList.get(i).isFinishing()) {
                activityList.get(i).finish();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化相关文件路径
        if (FileUtil.initPath(AppUtil.ROOT)) {
            Log.i("ZSApplication", "根路径初始化成功");
        }
        if (FileUtil.initPath(AppUtil.LOG_PATH)) {
            Log.i("ZSApplication", "日志路径初始化成功");
        }
        // 实例化日志
        if(!BuildConfig.DEBUG){
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext(), AppUtil.LOG_PATH);
        }
        regToWx();
    }

    public static IWXAPI api;

    private void regToWx() {
//        api = WXAPIFactory.createWXAPI(this, null);
//
        api = WXAPIFactory.createWXAPI(this, InternetUtil.getWChatAppId(), true);
        api.registerApp(InternetUtil.getWChatAppId());
    }
//
//    private void te() {
//        WXTextObject textObject = new WXTextObject();
//        textObject.text = text;
//
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObject;
//        msg.description = text;
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());//transaction用于唯一标识一个请求
//        req.message = msg;
//
//        api.sendReq(req);
//    }
}
