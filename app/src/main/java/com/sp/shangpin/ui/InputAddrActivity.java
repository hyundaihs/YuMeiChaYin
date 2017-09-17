package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.AddressInfo;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.OrdersInfo_Sup;
import com.sp.shangpin.entity.RequestAndResult;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.ReExpressUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/17/017.
 */

public class InputAddrActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private AutoCompleteTextView name, phone, area, addr, content;
    private int orderId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_addr);
        orderId = getIntent().getIntExtra("order_id", -1);
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.input_addr_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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
        Map<String, String> map = new HashMap<>();
        map.put("orderssj_id", String.valueOf(orderId));
        map.put("title", name.getText().toString());
        map.put("phone", phone.getText().toString());
        map.put("pca", area.getText().toString());
        map.put("address", addr.getText().toString());
        map.put("contents", content.getText().toString());
//        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
//        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjlists(),
//                JsonUtil.objectToString(map),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
//                        if (interResult.isSuccessed()) {
//                        } else {
//                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        DialogUtil.showErrorMessage(thisContext, error.toString());
//                    }
//                });
//        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
        if (orderId >= 0) {
            pickUp(map);
        }
    }

    private void pickUp(Map<String, String> map) {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjtihuo(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            finish();
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
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
