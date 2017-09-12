package com.sp.shangpin.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.GoodsDetails_Sup;
import com.sp.shangpin.entity.InterResult;
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

public class TopUpActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private RadioButton btn_price1, btn_price2, btn_price3, btn_price4,
            btn_price5, btn_price6, btn_price7, btn_price8;
    private ImageView wchat, aliPay;
    private Button topUp;

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
        btn_price1 = (RadioButton) findViewById(R.id.top_up_price1);
        btn_price1.setOnClickListener(this);
        btn_price2 = (RadioButton) findViewById(R.id.top_up_price2);
        btn_price2.setOnClickListener(this);
        btn_price3 = (RadioButton) findViewById(R.id.top_up_price3);
        btn_price3.setOnClickListener(this);
        btn_price4 = (RadioButton) findViewById(R.id.top_up_price4);
        btn_price4.setOnClickListener(this);
        btn_price5 = (RadioButton) findViewById(R.id.top_up_price5);
        btn_price5.setOnClickListener(this);
        btn_price6 = (RadioButton) findViewById(R.id.top_up_price6);
        btn_price6.setOnClickListener(this);
        btn_price7 = (RadioButton) findViewById(R.id.top_up_price7);
        btn_price7.setOnClickListener(this);
        btn_price8 = (RadioButton) findViewById(R.id.top_up_price8);
        btn_price8.setOnClickListener(this);
        topUp = (Button) findViewById(R.id.top_up_top_up);
        topUp.setOnClickListener(this);
    }

    private void refresh() {
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

    private void getGoodsDetails(int id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.goodssjinfo(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            GoodsDetails_Sup goodsDetails_sup = (GoodsDetails_Sup) JsonUtil.stringToObject(response.toString(), GoodsDetails_Sup.class);
                            refresh();
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
                setResult(12);
                finish();
                break;
            case R.id.top_up_price1:
                break;
        }
    }

}
