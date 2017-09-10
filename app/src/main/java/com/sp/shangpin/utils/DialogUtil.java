package com.sp.shangpin.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class DialogUtil {

    public static AlertDialog showAskMessage(Context context, String message,
                                             DialogInterface.OnClickListener positiveListener) {
        return createDialog(context, "提示", message, "确定", positiveListener, "取消", null);
    }

    public static AlertDialog showAskMessage(Context context, String message) {
        return createDialog(context, "提示", message, "确定", null, null, null);
    }

    public static AlertDialog showErrorMessage(Context context, String message) {
        return createDialog(context, "错误", message, "确定", null, null, null);
    }

    public static AlertDialog showErrorMessage(Context context, String message, DialogInterface.OnClickListener positiveListener) {
        return createDialog(context, "错误", message, "确定", positiveListener, null, null);
    }


    public static AlertDialog showMessage(Context context, String title, String message) {
        return createDialog(context, title, message, "确定", null, null, null);
    }

    public static AlertDialog createDialog(Context context, String title, String message,
                                           String positiveStr, DialogInterface.OnClickListener positiveListener,
                                           String negativeStr, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (null != title) {
            builder.setTitle(title);
        }
        if (null != message) {
            builder.setMessage(message);
        }
        if (null != positiveStr) {
            builder.setPositiveButton(positiveStr, positiveListener);
        }
        if (null != negativeStr) {
            builder.setNegativeButton(negativeStr, negativeListener);
        }
        builder.create();
        AlertDialog alertDialog = builder.show();
        return alertDialog;
    }
}
