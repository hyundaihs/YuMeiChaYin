package com.sp.shangpin.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.RadioGroup;
import android.widget.TextView;

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

public class RealNameActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;
    private TextView nickName;
    private AutoCompleteTextView phone, securityCode, name, identity;
    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    private Button submit;
    private SecurityCodeView securityCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_real_name);
        init();
    }

    private void init() {
        initActionBar();
        submit = (Button) findViewById(R.id.real_name_register);
        submit.setOnClickListener(this);
        nickName = (TextView) findViewById(R.id.real_name_nick_name);
        nickName.setText(MyApplication.userInfo.getTitle());
        securityCodeView = (SecurityCodeView) findViewById(R.id.real_name_security_code);
        securityCodeView.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.real_name_progress);
        phone = (AutoCompleteTextView) findViewById(R.id.real_name_phone_num);
        securityCode = (AutoCompleteTextView) findViewById(R.id.real_name_security_code_input);
        name = (AutoCompleteTextView) findViewById(R.id.real_name_name_input);
        identity = (AutoCompleteTextView) findViewById(R.id.real_name_identity_input);
        radioGroup = (RadioGroup) findViewById(R.id.real_name_sex_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            }
        });
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.real_name_toolbar);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.real_name_register:
                if (attemptSubmit()) {
                    submit();
                }
                break;
            case R.id.real_name_security_code:
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
        } else if (TextUtils.isEmpty(name.getText())) {
            name.setError("真实姓名不能为空");
        } else if (TextUtils.isEmpty(identity.getText())) {
            identity.setError("身份证号码不能为空");
        } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
            phone.setError("手机号格式不对");
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
        map.put("true_name", name.getText().toString());
        map.put("id_numbers", identity.getText().toString());
        map.put("sex", radioGroup.getCheckedRadioButtonId() == R.id.real_name_sex_man ? "男" : "女");
        map.put("verf", securityCode.getText().toString());
        map.put("phone", phone.getText().toString());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.smrz(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showProgress(false);
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            Log.d(TAG, response.toString());
                            DialogUtil.showAskMessage(thisContext, "提交成功");
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
        name.setVisibility(show ? View.GONE : View.VISIBLE);
        identity.setVisibility(show ? View.GONE : View.VISIBLE);
        submit.setVisibility(show ? View.GONE : View.VISIBLE);
        securityCodeView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
