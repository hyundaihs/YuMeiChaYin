package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
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
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.ReExpressUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/23/023.
 */

public class GetCashActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;
    private AutoCompleteTextView bankName, cardNumber, cashNumber, name, phone;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_get_cash);
        init();
    }

    private void init() {
        initActionBar();
        submit = (Button) findViewById(R.id.get_cash_submit);
        submit.setOnClickListener(this);
        bankName = (AutoCompleteTextView) findViewById(R.id.get_cash_bank_name);
        cardNumber = (AutoCompleteTextView) findViewById(R.id.get_cash_card_number);
        cashNumber = (AutoCompleteTextView) findViewById(R.id.get_cash_cash_number);
        phone = (AutoCompleteTextView) findViewById(R.id.get_cash_phone);
        name = (AutoCompleteTextView) findViewById(R.id.get_cash_name);
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("提现");
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
            case R.id.get_cash_submit:
                if (attemptSubmit()) {
                    submit();
                }
                break;
        }
    }

    private boolean attemptSubmit() {
        if (TextUtils.isEmpty(bankName.getText())) {
            bankName.setError("开户行不能为空");
        } else if (TextUtils.isEmpty(cardNumber.getText())) {
            cardNumber.setError("银行卡号不能为空");
        } else if (TextUtils.isEmpty(cashNumber.getText())) {
            cashNumber.setError("提现金额不能为空");
        } else if (TextUtils.isEmpty(name.getText())) {
            name.setError("姓名不能为空");
        } else if (TextUtils.isEmpty(phone.getText())) {
            phone.setError("手机号不能为空");
        } else if (!ReExpressUtil.isMatcher(ReExpressUtil.PHONE, phone.getText().toString())) {
            phone.setError("手机号格式不对");
        } else {
            return true;
        }
        return false;
    }


    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("bank_name", bankName.getText().toString());
        map.put("card_numbers", cardNumber.getText().toString());
        map.put("price", cashNumber.getText().toString());
        map.put("title", name.getText().toString());
        map.put("phone", phone.getText().toString());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.txsq(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            DialogUtil.showAskMessage(thisContext, "提交成功", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        } else {
                            Log.d(TAG, interResult.toString());
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.fpass());
    }

}
