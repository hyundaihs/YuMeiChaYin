package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.utils.IntentUtil;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.ReExpressUtil;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/17/017.
 */

public class InputAddrActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private AutoCompleteTextView name, phone, area, addr, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_addr);
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.input_addr_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews() {
        name = (AutoCompleteTextView) findViewById(R.id.input_addr_name);
        phone = (AutoCompleteTextView) findViewById(R.id.input_addr_phone);
        area = (AutoCompleteTextView) findViewById(R.id.input_addr_area);
        addr = (AutoCompleteTextView) findViewById(R.id.input_addr_addr);
        content = (AutoCompleteTextView) findViewById(R.id.input_addr_content);
        findViewById(R.id.input_addr_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showAskMessage(thisContext, "确定要提交吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (check()) {
                            submit();
                        }
                    }
                });
            }
        });
        if (null != MyApplication.userInfo && !TextUtils.isEmpty(MyApplication.userInfo.getWl_title())) {
            name.setText(MyApplication.userInfo.getWl_title());
            phone.setText(MyApplication.userInfo.getWl_phone());
            area.setText(MyApplication.userInfo.getWl_pca());
            addr.setText(MyApplication.userInfo.getWl_address());
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("收货人不能为空");
        } else if (TextUtils.isEmpty(phone.getText())) {
            name.setError("手机号不能为空");
        } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
            phone.setError("手机号格式不对");
        } else if (TextUtils.isEmpty(area.getText())) {
            name.setError("地区不能为空");
        } else if (TextUtils.isEmpty(addr.getText())) {
            name.setError("详细地址不能为空");
        } else {
            return true;
        }
        return false;
    }

    private void submit() {
        /**
         * 提交给服务器,成功后关闭页面   返回OK  并返回地址信息
         */
        MyApplication.userInfo.setWl_title(name.getText().toString());
        MyApplication.userInfo.setWl_phone(phone.getText().toString());
        MyApplication.userInfo.setWl_pca(area.getText().toString());
        MyApplication.userInfo.setWl_address(addr.getText().toString());
        MyApplication.userInfo.setWl_content(content.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("position", getIntent().getIntExtra("position", -1));
        setResult(IntentUtil.RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
