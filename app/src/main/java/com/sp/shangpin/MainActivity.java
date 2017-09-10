package com.sp.shangpin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LoginInfo_Sup;
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
    }

    private void isRemember() {
        if ((Boolean) SharedPreferencesUtil.getParam(this, SharedKey.IS_REMEMBER, false)) {
            verfLogin((String) SharedPreferencesUtil.getParam(this, SharedKey.LOGIN_VERF, ""));
        } else {
            init();
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
                            DialogUtil.showAskMessage(thisContext, "登录成功");
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            MyApplication.cleanAllActivitys();
                        } else {
                            SharedPreferencesUtil.setParam(MainActivity.this, SharedKey.IS_REMEMBER, false);
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                            init();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sysinfo(),
                JsonUtil.objectToString(new HashMap<String, String>()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SystemInfo_Sup systemInfoSup = (SystemInfo_Sup) JsonUtil.stringToObject(response.toString(), SystemInfo_Sup.class);
                        if (systemInfoSup.isSuccessed()) {
                            MyApplication.systemInfo = systemInfoSup.getRetRes();
                            isRemember();
                        } else {
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
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.main_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.main_wchat_login_btn:
                break;
        }
    }
}
