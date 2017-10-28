package com.sp.shangpin.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.WXUserInfo;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.LoginUtil;
import com.sp.shangpin.utils.ReExpressUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.ToastUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.SecurityCodeView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/29/029.
 */

public class BindPhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private AutoCompleteTextView phone, securityCode;
    private SecurityCodeView securityBtn;
    private Button submit;
    private WXUserInfo wxUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        wxUserInfo = (WXUserInfo) getIntent().getExtras().get("user_info");
        if (null != wxUserInfo) {
            init();
        }
    }

    private void init() {
        initActionBar();
        submit = (Button) findViewById(R.id.bind_phone_submit);
        phone = (AutoCompleteTextView) findViewById(R.id.bind_phone_phone_num);
        securityCode = (AutoCompleteTextView) findViewById(R.id.bind_phone_security_code_input);
        securityBtn = (SecurityCodeView) findViewById(R.id.bind_phone_security_code);
        submit.setOnClickListener(this);
        securityBtn.setOnClickListener(this);
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("绑定手机号码");
        setSupportActionBar(toolbar);
        setTitle("");
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
            case R.id.bind_phone_submit:
                if (attemptBind()) {
                    bindPhone();
                }
                break;
            case R.id.bind_phone_security_code:
                if (TextUtils.isEmpty(phone.getText())) {
                    phone.setError("手机号不能为空");
                    return;
                } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
                    phone.setError("手机号格式不对");
                    return;
                }
                getSecurityCode();
                securityBtn.startCount();
                break;
        }
    }

    private boolean attemptBind() {
        if (TextUtils.isEmpty(phone.getText())) {
            phone.setError("手机号不能为空");
        } else if (TextUtils.isEmpty(securityCode.getText())) {
            securityCode.setError("验证码不能为空");
        } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
            phone.setError("手机号格式不对");
        } else {
            return true;
        }
        return false;
    }

    private void getSecurityCode() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone.getText().toString());
        map.put("type", "reg");
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

    private void bindPhone() {
        Map<String, String> map = new HashMap<>();
        map.put("account", phone.getText().toString());
        map.put("openid", wxUserInfo.getOpenid());
        map.put("verf", securityCode.getText().toString());
        map.put("title", wxUserInfo.getNickname());
        map.put("file_url", wxUserInfo.getHeadimgurl());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.reg(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            ToastUtil.show(thisContext, "绑定成功");
                            login(wxUserInfo.getOpenid());
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

    private void login(final String openid) {
        LoginUtil.verfLogin(thisContext, true, openid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                if (interResult.isSuccessed()) {
                    ToastUtil.show(thisContext, "登录成功");
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, true);
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.ISWX_LOGIN, true);
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.OPENID, openid);
                    startActivity(new Intent(thisContext, HomeActivity.class));
                    MyApplication.cleanAllActivitys();
                    finish();
                } else {
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtil.showErrorMessage(thisContext, error.toString());
            }
        });
    }
}
