package com.sp.shangpin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.SystemInfo_Sup;
import com.sp.shangpin.ui.HomeActivity;
import com.sp.shangpin.ui.LoginActivity;
import com.sp.shangpin.ui.RegisterActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/7.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_main);
        getSystemInfo();
        init();
    }

    private void isRemember() {
        if ((Boolean) SharedPreferencesUtil.getParam(thisContext, SharedKey.IS_REMEMBER, false)) {
            Log.i(TAG,"自动登录验证");
            verfLogin((String) SharedPreferencesUtil.getParam(this, SharedKey.LOGIN_VERF, ""));
        }
    }

    private void verfLogin(final String verf) {
        Map<String, String> map = new HashMap<>();
        map.put("login_verf", verf);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.verflogin(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            Log.i(TAG, "自动登录成功");
                            DialogUtil.showAskMessage(thisContext, "登录成功");
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            MyApplication.cleanAllActivitys();
                        } else {
                            Log.e(TAG, "自动登录失败," + interResult.getRetErr());
                            SharedPreferencesUtil.setParam(MainActivity.this, SharedKey.IS_REMEMBER, false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        DialogUtil.showErrorMessage(thisContext, error.toString(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());

    }

    private void getSystemInfo() {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sysinfo(),
                JsonUtil.objectToString(new HashMap<String, String>()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SystemInfo_Sup systemInfoSup = (SystemInfo_Sup) JsonUtil.stringToObject(response.toString(), SystemInfo_Sup.class);
                        if (systemInfoSup.isSuccessed()) {
                            Log.i(TAG, "系统信息获取成功");
                            MyApplication.systemInfo = systemInfoSup.getRetRes();
                            isRemember();
                        } else {
                            Log.e(TAG, "系统信息获取失败," + systemInfoSup.getRetErr());
                            DialogUtil.showErrorMessage(thisContext, systemInfoSup.getRetErr(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        DialogUtil.showErrorMessage(thisContext, error.toString(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
    }

    private void init() {
        findViewById(R.id.main_login).setOnClickListener(this);
        findViewById(R.id.main_register).setOnClickListener(this);
        findViewById(R.id.main_wchat_login_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_login:
                startActivity(new Intent(thisContext, LoginActivity.class));
                break;
            case R.id.main_register:
                startActivity(new Intent(thisContext, RegisterActivity.class));
                break;
            case R.id.main_wchat_login_btn:
                break;
        }
    }
}
