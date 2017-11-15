package com.sp.shangpin.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sp.shangpin.MainActivity;
import com.sp.shangpin.R;

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

    public static AlertDialog showAskMessage(Context context, String message, String ok,
                                             DialogInterface.OnClickListener positiveListener) {
        return createDialog(context, "提示", message, null == ok ? "确定" : ok, positiveListener, "取消", null);
    }

    public static AlertDialog showErrorMessage(Context context, String message) {
        return showErrorMessage(context, message, null);
    }

    public static AlertDialog showErrorMessage(final Context context, String message, DialogInterface.OnClickListener positiveListener) {
        AlertDialog dialog;
        if (message.contains("loginerr")) {
            dialog = createDialog(context, "错误", message, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            }, null, null);
        } else {
            dialog = createDialog(context, "错误", message, "确定", positiveListener, null, null);
        }
        return dialog;
    }


    public static AlertDialog showTipMessage(Context context, String message, DialogInterface.OnClickListener positiveListener) {
        return createDialog(context, "提示", message, "确定", positiveListener, null, null);
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

    public static void createShareDialog(final Context context, final View.OnClickListener onClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_dialog, null);
        TextView friendArea = view.findViewById(R.id.share_friend_are);
        TextView wxFriend = view.findViewById(R.id.share_wx_friend);
        TextView weiBo = view.findViewById(R.id.share_weibo);

        final Dialog mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        friendArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
                mBottomSheetDialog.dismiss();
            }
        });
        wxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
                mBottomSheetDialog.dismiss();
            }
        });
        weiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
                mBottomSheetDialog.dismiss();
            }
        });
    }
}
