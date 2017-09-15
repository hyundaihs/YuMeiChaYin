package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/11.
 * 充值页面
 */

public class TopUpActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private CheckBox[] checkBoxes = new CheckBox[8];
    private RadioGroup radioGroup;
    private Button topUp;
    private AutoCompleteTextView elsePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        init();
    }

    private void init() {
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_up_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void initViews() {
        checkBoxes[0] = (CheckBox) findViewById(R.id.top_up_price1);
        checkBoxes[0].setOnCheckedChangeListener(this);
        checkBoxes[1] = (CheckBox) findViewById(R.id.top_up_price2);
        checkBoxes[1].setOnCheckedChangeListener(this);
        checkBoxes[2] = (CheckBox) findViewById(R.id.top_up_price3);
        checkBoxes[2].setOnCheckedChangeListener(this);
        checkBoxes[3] = (CheckBox) findViewById(R.id.top_up_price4);
        checkBoxes[3].setOnCheckedChangeListener(this);
        checkBoxes[4] = (CheckBox) findViewById(R.id.top_up_price5);
        checkBoxes[4].setOnCheckedChangeListener(this);
        checkBoxes[5] = (CheckBox) findViewById(R.id.top_up_price6);
        checkBoxes[5].setOnCheckedChangeListener(this);
        checkBoxes[6] = (CheckBox) findViewById(R.id.top_up_price7);
        checkBoxes[6].setOnCheckedChangeListener(this);
        checkBoxes[7] = (CheckBox) findViewById(R.id.top_up_price8);
        checkBoxes[7].setOnCheckedChangeListener(this);
        elsePrice = (AutoCompleteTextView) findViewById(R.id.top_up_else_price);
        radioGroup = (RadioGroup) findViewById(R.id.top_up_pay_type);
        topUp = (Button) findViewById(R.id.top_up_top_up);
        topUp.setOnClickListener(this);
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

    private void TopUp() {
        Map<String, String> map = new HashMap<>();
        map.put("price", getPrice());
        map.put("pay_type", getPayType());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.cz(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            DialogUtil.showTipMessage(thisContext, "充值成功", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    setResult(12);
                                    finish();
                                }
                            });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_up_top_up:
                TopUp();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            checked(compoundButton.getId());
            elsePrice.setVisibility(compoundButton.getId() == checkBoxes[checkBoxes.length - 1].getId()
                    ? View.VISIBLE : View.GONE);
        } else {
            int id = 0;
            for (CheckBox checkBoxe : checkBoxes) {
                if (checkBoxe.isChecked()) {
                    id = checkBoxe.getId();
                }
            }
            if (id == 0) {
                compoundButton.setChecked(true);
            }
        }
    }

    private void checked(int id) {
        for (CheckBox checkBoxe : checkBoxes) {
            if (checkBoxe.isChecked() && checkBoxe.getId() != id) {
                checkBoxe.setChecked(false);
            }
        }
    }

    private String getPayType() {
        String type;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.top_up_wchat:
                type = SharedKey.WEIXIN;
                break;
            case R.id.top_up_alipay:
                type = SharedKey.ALIPAY;
                break;
            default:
                type = SharedKey.BANK;
                break;
        }
        return type;
    }

    private String getPrice() {
        String price = "0";
        for (CheckBox checkBoxe : checkBoxes) {
            if (checkBoxe.isChecked()) {
                if (checkBoxe.getId() == checkBoxes[checkBoxes.length - 1].getId()) {
                    if (!TextUtils.isEmpty(elsePrice.getText())) {
                        price = elsePrice.getText().toString();
                    }
                } else {
                    price = checkBoxe.getText().toString();
                }
            }
        }
        return price;
    }
}
