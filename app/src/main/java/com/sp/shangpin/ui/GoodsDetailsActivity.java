package com.sp.shangpin.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.GoodsDetails_Sup;
import com.sp.shangpin.entity.LoginInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.UpgradeGoods;
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
 * Created by Administrator on 2017/9/10/010.
 */

public class GoodsDetailsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private UpgradeGoods upgradeGoods;
    private ImageView goodsImage;
    private TextView goodsName, goodsPrice, goodsFreight, goodsIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        init();
    }

    private void init() {
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void initViews() {
        int id = getIntent().getIntExtra("id", -1);
        goodsImage = (ImageView) findViewById(R.id.goods_details_image);
        goodsName = (TextView) findViewById(R.id.goods_details_name);
        goodsPrice = (TextView) findViewById(R.id.goods_details_price);
        goodsFreight = (TextView) findViewById(R.id.goods_details_freight);
        goodsIntroduce = (TextView) findViewById(R.id.goods_details_introduce);
        if (id > 0) {
            getGoodsDetails(id);
        }
    }

    private void refresh() {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        volleyUtil.getImage(goodsImage, upgradeGoods.getInfo_file_url());
        goodsName.setText(upgradeGoods.getTitle());
        goodsPrice.setText("单价：￥" + upgradeGoods.getPrice());
        goodsFreight.setText("邮费：￥" + upgradeGoods.getYf() + "(运费首件" + upgradeGoods.getYf() + "元，此后每件依次加" + upgradeGoods.getYf_one() + "元)");
        goodsIntroduce.setText(Html.fromHtml(upgradeGoods.getApp_contents()));
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
                        GoodsDetails_Sup goodsDetails_sup = (GoodsDetails_Sup) JsonUtil.stringToObject(response.toString(), GoodsDetails_Sup.class);
                        if (goodsDetails_sup.isSuccessed()) {
                            upgradeGoods = goodsDetails_sup.getRetRes();
                            refresh();
                        } else {
                            DialogUtil.showErrorMessage(thisContext, goodsDetails_sup.getRetErr());
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
}
