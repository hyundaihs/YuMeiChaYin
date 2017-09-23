package com.sp.shangpin.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.ReExpressUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.SecurityCodeView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/23/023.
 */

public class FindPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;
    private AutoCompleteTextView phone, securityCode, password, confirmPwd;
    private ProgressBar progressBar;
    private Button submit;
    private SecurityCodeView securityCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_find_password);
        init();
    }

    private void init() {
        initActionBar();
        submit = (Button) findViewById(R.id.find_pass_register);
        submit.setOnClickListener(this);
        securityCodeView = (SecurityCodeView) findViewById(R.id.find_pass_security_code);
        securityCodeView.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.find_pass_progress);
        phone = (AutoCompleteTextView) findViewById(R.id.find_pass_phone_num);
        securityCode = (AutoCompleteTextView) findViewById(R.id.find_pass_security_code_input);
        password = (AutoCompleteTextView) findViewById(R.id.find_pass_password_input);
        confirmPwd = (AutoCompleteTextView) findViewById(R.id.find_pass_confirm_password_input);
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.find_pass_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_pass_register:
                if (attemptSubmit()) {
                    submit();
                }
                break;
            case R.id.find_pass_security_code:
                if (TextUtils.isEmpty(phone.getText())) {
                    phone.setError("手机号不能为空");
                    return;
                } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
                    phone.setError("手机号格式不对");
                    return;
                }
                getSecurityCode();
                securityCodeView.startCount();
                break;
        }
    }

    private boolean attemptSubmit() {
        if (TextUtils.isEmpty(phone.getText())) {
            phone.setError("手机号不能为空");
        } else if (TextUtils.isEmpty(securityCode.getText())) {
            securityCode.setError("验证码不能为空");
        } else if (TextUtils.isEmpty(password.getText())) {
            password.setError("密码不能为空");
        } else if (TextUtils.isEmpty(confirmPwd.getText())) {
            confirmPwd.setError("确认密码不能为空");
        } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
            phone.setError("手机号格式不对");
        } else if (!password.getText().toString().equals(confirmPwd.getText().toString())) {
            confirmPwd.setError("两次密码不一样");
        } else {
            showProgress(true);
            return true;
        }
        return false;
    }

    private void getSecurityCode() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone.getText().toString());
        map.put("type", "reset");
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        final JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sendmsg(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            Log.d(TAG, "获取短信验证码成功");
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
        volleyUtil.addToRequestQueue(request, InternetUtil.sendmsg());
    }

    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("account", phone.getText().toString());
        map.put("password", password.getText().toString());
        map.put("verf", securityCode.getText().toString());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.fpass(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showProgress(false);
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            Log.d(TAG,response.toString());
                            DialogUtil.showAskMessage(thisContext, "密码设置成功");
                            finish();
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false);
                        DialogUtil.showErrorMessage(thisContext, error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.fpass());
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        phone.setVisibility(show ? View.GONE : View.VISIBLE);
        securityCode.setVisibility(show ? View.GONE : View.VISIBLE);
        password.setVisibility(show ? View.GONE : View.VISIBLE);
        confirmPwd.setVisibility(show ? View.GONE : View.VISIBLE);
        submit.setVisibility(show ? View.GONE : View.VISIBLE);
        securityCodeView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}