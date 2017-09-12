package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.GoodsDetails_Sup;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/10/010.
 * 商品详情页面
 */

public class GoodsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private UpgradeGoods upgradeGoods;
    private ImageView goodsImage;
    private TextView goodsName, goodsPrice, goodsFreight, goodsIntroduce;
    private TextView balance, buy;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.goods_details_toolbar);
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
        balance = (TextView) findViewById(R.id.goods_details_balance);
        buy = (TextView) findViewById(R.id.goods_details_buy);
        buy.setOnClickListener(this);
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
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                        GoodsDetails_Sup goodsDetails_sup = (GoodsDetails_Sup) JsonUtil.stringToObject(response.toString(), GoodsDetails_Sup.class);
                            upgradeGoods = goodsDetails_sup.getRetRes();
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
            case R.id.goods_details_buy:
                buyPay();
                break;
        }
    }

    private void buyPay() {
        /**
         * 判断余额是否足够
         */
        //不足够充值
        DialogUtil.showAskMessage(this, "余额不足,是否立即充值?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent(thisContext, TopUpActivity.class), 11);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 12) {
            DialogUtil.createDialog(this, "提示", "你有一次参与抽奖机会.", "参与", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(thisContext, LotteryActivity.class));
                }
            }, "不参与", null);
        }
    }
}
