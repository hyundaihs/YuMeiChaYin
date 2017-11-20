package com.sp.shangpin.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LoginInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.LoginUtil;
import com.sp.shangpin.utils.ReExpressUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.SecurityCodeView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/7.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;
    private AutoCompleteTextView phone, securityCode, password, confirmPwd, recommendCode;
    private Button register, alreadyRegist;
    private SecurityCodeView securityCodeView;
    private View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        initActionBar();
        content = findViewById(R.id.register_content);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        volleyUtil.getBitmap(MyApplication.getSystemInfo().getZc_file_url(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(response.getBitmap());
                content.setBackground(bitmapDrawable);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        register = (Button) findViewById(R.id.register_register);
        register.setOnClickListener(this);
        alreadyRegist = (Button) findViewById(R.id.register_already_registered);
        alreadyRegist.setOnClickListener(this);
        securityCodeView = (SecurityCodeView) findViewById(R.id.register_security_code);
        securityCodeView.setOnClickListener(this);
        phone = (AutoCompleteTextView) findViewById(R.id.register_phone_num);
        securityCode = (AutoCompleteTextView) findViewById(R.id.register_security_code_input);
        password = (AutoCompleteTextView) findViewById(R.id.register_password_input);
        confirmPwd = (AutoCompleteTextView) findViewById(R.id.register_confirm_password_input);
        recommendCode = (AutoCompleteTextView) findViewById(R.id.register_recommend_code_input);
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("注册");
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
            case R.id.register_register:
                if (attemptRegister()) {
                    register();
                }
                break;
            case R.id.register_already_registered:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.register_security_code:
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

    private boolean attemptRegister() {
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
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.sendmsg());
    }

    private void register() {
        final Map<String, String> map = new HashMap<>();
        map.put("account", phone.getText().toString());
        map.put("password", password.getText().toString());
        map.put("verf", securityCode.getText().toString());
        if (!TextUtils.isEmpty(recommendCode.getText())) {
            map.put("numbers", recommendCode.getText().toString());
        }
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.reg(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            login(map.get("account"),map.get("password"));
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());

    }

    private void login(final String account, final String password) {
        LoginUtil.login(thisContext,account,password,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //从服务器响应response中的jsonObject中取出cookie的值，存到本地sharePreference
                InterResult interResult =
                        (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                if (interResult.isSuccessed()) {
                    LoginInfo_Sup loginInfo_sup = (LoginInfo_Sup) JsonUtil.stringToObject(response.toString(), LoginInfo_Sup.class);
                    DialogUtil.showAskMessage(thisContext, "登录成功");
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, true);
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.ISWX_LOGIN, false);
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.LOGIN_VERF, loginInfo_sup.getRetRes().getLogin_verf());
                    startActivity(new Intent(thisContext, HomeActivity.class));
                    MyApplication.cleanAllActivitys();
                } else {
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, false);
                    DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                }
            }
        }, new RequestUtil.MyErrorListener() {
            @Override
            public void onErrorResponse(String error) {
                DialogUtil.showErrorMessage(thisContext, error.toString());
            }
        });
    }

}
