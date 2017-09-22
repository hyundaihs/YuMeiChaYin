package com.sp.shangpin.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/22.
 */

public class ToastUtil {
    public static void show(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
