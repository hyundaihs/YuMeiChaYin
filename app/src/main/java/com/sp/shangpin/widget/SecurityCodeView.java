package com.sp.shangpin.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.sp.shangpin.R;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/7.
 */

public class SecurityCodeView extends android.support.v7.widget.AppCompatButton {

    private static final int PRE_COUNT_TIME = 60;
    private boolean flag = false;
    private int countTime = PRE_COUNT_TIME;
    private OnClickListener onClickListener;
    private OnNumberCountListener onNumberCountListener;
    private Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setText(msg.what == 0 ? countTime-- + " s" : context.getString(R.string.register_get_security_code));
            if (null != onNumberCountListener) {
                onNumberCountListener.onNumberCounting(msg.what == 0 ?
                        OnNumberCountListener.Status.COUNTING : OnNumberCountListener.Status.END);
            }
        }
    };

    public SecurityCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setText(context.getString(R.string.register_get_security_code));
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {
                    if (null != onClickListener) {
                        onClickListener.onClick(view);
                    }
                    if (null != onNumberCountListener) {
                        onNumberCountListener.onNumberCounting(OnNumberCountListener.Status.START);
                    }
                }
            }
        });
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnNumberCountListener(OnNumberCountListener onNumberCountListener) {
        this.onNumberCountListener = onNumberCountListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void startCount() {
        flag = true;
        new Thread(new MyRunnable()).start();
    }

    private void stopCount() {
        flag = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        stopCount();
        super.onDetachedFromWindow();
    }

    public interface OnNumberCountListener {
        void onNumberCounting(Status status);

        enum Status

        {
            START, COUNTING, END
        }
    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            while (flag) {
                if (countTime <= 0) {
                    stopCount();
                    countTime = PRE_COUNT_TIME;
                    handler.sendEmptyMessage(1);
                    break;
                }
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
