package com.sp.shangpin.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sp.shangpin.R;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/7.
 */

public class CountNumberView extends LinearLayout implements View.OnClickListener {

    private TextView num,del, add;
    private int currNum = 1;
    private int max = -1;
    private OnNumberChangerListener onNumberChangerListener;

    public CountNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_count_number_view, null, false);
        num = view.findViewById(R.id.count_number_num);
        del = view.findViewById(R.id.count_number_del);
        add = view.findViewById(R.id.count_number_add);
        addView(view);
        del.setOnClickListener(this);
        add.setOnClickListener(this);
        num.setText(String.valueOf(currNum));
        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (currNum <= 1) {
                    del.setOnClickListener(null);
                } else {
                    del.setOnClickListener(CountNumberView.this);
                }
                if (max > 0) {
                    if (currNum >= max) {
                        add.setOnClickListener(null);
                    } else {
                        add.setOnClickListener(CountNumberView.this);
                    }
                }
                if (null != onNumberChangerListener) {
                    onNumberChangerListener.onNumberChange(currNum);
                }
            }
        });
    }

    public OnNumberChangerListener getOnNumberChangerListener() {
        return onNumberChangerListener;
    }

    public void setOnNumberChangerListener(OnNumberChangerListener onNumberChangerListener) {
        this.onNumberChangerListener = onNumberChangerListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public int getCurrNum() {
        return currNum;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.count_number_add:
                num.setText(String.valueOf(++currNum));
                break;
            case R.id.count_number_del:
                num.setText(String.valueOf(--currNum));
                break;
        }
    }

    public interface OnNumberChangerListener {
        void onNumberChange(int curr);
    }
}