package com.sp.shangpin.ui;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LoginInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.ReExpressUtil;
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

public class LoginActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private AutoCompleteTextView phone, password;
    private ProgressBar progressBar;
    private Button login, forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        initActionBar();
        phone = (AutoCompleteTextView) findViewById(R.id.login_phone_num);
        password = (AutoCompleteTextView) findViewById(R.id.login_password_input);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        login = (Button) findViewById(R.id.login_login);
        forget = (Button) findViewById(R.id.login_forget);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptLogin()) {
                    showProgress(true);
                    login(phone.getText().toString(), password.getText().toString());
                }
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(thisContext, FindPasswordActivity.class));
            }
        });
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    private boolean attemptLogin() {
        if (TextUtils.isEmpty(phone.getText())) {
            phone.setError("手机号不能为空");
        } else if (TextUtils.isEmpty(password.getText())) {
            password.setError("密码不能为空");
        } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
            password.setError("密码不能为空");
        } else {
            return true;
        }
        return false;
    }


    private void login(final String account, final String password) {
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("password", password);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.login(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //从服务器响应response中的jsonObject中取出cookie的值，存到本地sharePreference
                        showProgress(false);
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            LoginInfo_Sup loginInfo_sup = (LoginInfo_Sup) JsonUtil.stringToObject(response.toString(), LoginInfo_Sup.class);
                            DialogUtil.showAskMessage(thisContext, "登录成功");
                            SharedPreferencesUtil.setParam(LoginActivity.this, SharedKey.IS_REMEMBER, true);
                            SharedPreferencesUtil.setParam(LoginActivity.this, SharedKey.LOGIN_VERF, loginInfo_sup.getRetRes().getLogin_verf());
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            MyApplication.cleanAllActivitys();
                        } else {
                            SharedPreferencesUtil.setParam(LoginActivity.this, SharedKey.IS_REMEMBER, false);
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
        volleyUtil.addToRequestQueue(request, InternetUtil.login());

    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        phone.setVisibility(show ? View.GONE : View.VISIBLE);
        password.setVisibility(show ? View.GONE : View.VISIBLE);
        login.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}