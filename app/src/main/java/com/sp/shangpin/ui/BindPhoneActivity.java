package com.sp.shangpin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sp.shangpin.entity.WXUserInfo;
import com.sp.shangpin.utils.ToastUtil;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/29/029.
 */

public class BindPhoneActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXUserInfo wxUserInfo = (WXUserInfo) getIntent().getExtras().get("user_info");
        ToastUtil.show(this, wxUserInfo == null ? "userInfo is null " : wxUserInfo.toString());
    }
}
