package com.sp.shangpin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sp.shangpin.entity.SystemInfo;
import com.sp.shangpin.entity.UserInfo;
import com.sp.shangpin.utils.DialogUtil;

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
    public static int lottoCurrIndex = -1;

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
    }
}
