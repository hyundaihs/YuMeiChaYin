package com.sp.shangpin.utils;

import android.os.Environment;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/24/024.
 */

public class AppUtil {
    public static final String SDCARD = Environment
            .getExternalStorageDirectory().getPath();
    public static final String ROOT = SDCARD + "/com.sp.chayin/";
    public static final String LOG_PATH = ROOT + "log/";
}
