package com.sp.shangpin.ui;

import android.content.Context;
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
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.utils.IntentUtil;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.SharedKey;
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
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/22.
 */

public class AlertPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;
    private AutoCompleteTextView oPassword, nPassword, cPassword;
    private ProgressBar progressBar;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_alert_password);
        init();
    }

    private void init() {
        initActionBar();
        oPassword = (AutoCompleteTextView) findViewById(R.id.alert_pass_opassword);
        nPassword = (AutoCompleteTextView) findViewById(R.id.alert_pass_npassword);
        cPassword = (AutoCompleteTextView) findViewById(R.id.alert_pass_cpassword);
        submit = (Button) findViewById(R.id.alert_pass_submit);
        submit.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.alert_pass_progress);
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("修改密码");
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
            case R.id.alert_pass_submit:
                if (attemptSubmit()) {
                    submit();
                }
                break;
        }
    }

    private boolean attemptSubmit() {
        if (TextUtils.isEmpty(oPassword.getText())) {
            oPassword.setError("原密码不能为空");
        } else if (TextUtils.isEmpty(nPassword.getText())) {
            nPassword.setError("新密码不能为空");
        } else if (TextUtils.isEmpty(cPassword.getText())) {
            cPassword.setError("确认密码不能为空");
        } else if (!nPassword.getText().toString().equals(cPassword.getText().toString())) {
            nPassword.setError("两次密码不一样");
        } else {
            showProgress(true);
            return true;
        }
        return false;
    }

    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("oldpassword", oPassword.getText().toString());
        map.put("password", nPassword.getText().toString());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.repass(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showProgress(false);
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            DialogUtil.showAskMessage(thisContext, "修改成功,请重新登录");
                            SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, false);
                            setResult(IntentUtil.RESULT_OK);
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
        volleyUtil.addToRequestQueue(request, InternetUtil.repass());

    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
