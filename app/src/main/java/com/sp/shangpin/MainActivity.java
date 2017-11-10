package com.sp.shangpin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.SystemInfo_Sup;
import com.sp.shangpin.ui.HomeActivity;
import com.sp.shangpin.ui.LoginActivity;
import com.sp.shangpin.ui.RegisterActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonObjectPostRequest;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.LoginUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
            Log.i(TAG, "自动登录验证");
            boolean isWx = (boolean) SharedPreferencesUtil.getParam(this, SharedKey.ISWX_LOGIN, false);
            verfLogin(isWx, (String) SharedPreferencesUtil.getParam(this, isWx ? SharedKey.OPENID : SharedKey.LOGIN_VERF, ""));
        }
        final View content = findViewById(R.id.main_content);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        volleyUtil.getBitmap(MyApplication.getSystemInfo().getDl_file_url(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(response.getBitmap());
                content.setBackground(bitmapDrawable);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void verfLogin(boolean isWx, final String string) {
        LoginUtil.verfLogin(thisContext, isWx, string, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                if (interResult.isSuccessed()) {
                    Log.i(TAG, "自动登录成功");
                    DialogUtil.showAskMessage(thisContext, "登录成功");
                    startActivity(new Intent(thisContext, HomeActivity.class));
                    MyApplication.cleanAllActivitys();
                } else {
                    Log.e(TAG, "自动登录失败," + response.toString());
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, false);
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
    }

    private void getSystemInfo() {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sysinfo(),
                JsonUtil.objectToString(new HashMap<String, String>()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            SystemInfo_Sup systemInfoSup = (SystemInfo_Sup) JsonUtil.stringToObject(response.toString(), SystemInfo_Sup.class);
                            Log.i(TAG, "系统信息获取成功");
                            MyApplication.setSystemInfo(systemInfoSup.getRetRes());
                            isRemember();
                        } else {
                            Log.e(TAG, "系统信息获取失败," + response.toString());
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr(), new DialogInterface.OnClickListener() {
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
        volleyUtil.addToRequestQueue(request, InternetUtil.sysinfo());
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
                if (wchatLogin()) {
                    Log.d(TAG, "微信登录接入成功");
                } else {
                    Log.d(TAG, "微信登录接入失败");
                }
                break;
        }
    }

    private boolean wchatLogin() {
        Log.d(TAG, "send oauth request");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        return MyApplication.getApi().sendReq(req);
    }
}
