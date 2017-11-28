package com.sp.shangpin.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LoginInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.LoginUtil;
import com.sp.shangpin.utils.ReExpressUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/7.
 */

public class LoginActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private AutoCompleteTextView phone, password;
    private Button login, forget;
    private View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        initActionBar();
        content = findViewById(R.id.login_content);
        phone = (AutoCompleteTextView) findViewById(R.id.login_phone_num);
        password = (AutoCompleteTextView) findViewById(R.id.login_password_input);
        login = (Button) findViewById(R.id.login_login);
        forget = (Button) findViewById(R.id.login_forget);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptLogin()) {
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
        if (null != MyApplication.getSystemInfo()) {
            String url = MyApplication.getSystemInfo().getDl_file_url();
            VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
            volleyUtil.getBitmap(url, new ImageLoader.ImageListener() {
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
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("登录");
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

    private boolean attemptLogin() {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            phone.setError("手机号不能为空");
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("密码不能为空");
        } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
            phone.setError("手机格式不对");
        } else {
            return true;
        }
        return false;
    }


    private void login(final String account, final String password) {
        LoginUtil.login(thisContext, account, password, new Response.Listener<JSONObject>() {
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