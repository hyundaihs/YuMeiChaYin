package com.sp.shangpin.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.sp.shangpin.R;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/22.
 */

public class ToastUtil {
    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String text, int second) {
        Toast.makeText(context, text, second).show();
    }

    public static void show(Context context, String message, String url, int duration) {
        if(null == context){
            return;
        }
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null, false);
        //初始化布局控件
        TextView textView = toastRoot.findViewById(R.id.toast_text);
        ImageView imageView = toastRoot.findViewById(R.id.toast_image);
        //为控件设置属性
        textView.setText(message);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(context);
        volleyUtil.getImageByIntact(imageView, url, 64);
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.TOP, 0, height / 7);
        toastStart.setDuration(duration);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}
