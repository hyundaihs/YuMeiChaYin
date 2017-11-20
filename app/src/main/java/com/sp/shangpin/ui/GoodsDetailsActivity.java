package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.GoodsDetails_Sup;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.OrderInfo;
import com.sp.shangpin.entity.OrderInfo_Sup;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.entity.UserInfo_Sup;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.HtmlUtil;
import com.sp.shangpin.utils.IntentUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.CountNumberView;

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
    private TextView goodsName, goodsPrice, goodsFreight;
    private WebView goodsIntroduce;
    private TextView balance, buy;
    private CountNumberView countView;
    private int goodsId;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("升级商品详情");
        setSupportActionBar(toolbar);
        setTitle("");


    }

    private void initViews() {
        goodsId = getIntent().getIntExtra("id", -1);
        goodsImage = (ImageView) findViewById(R.id.goods_details_image);
        goodsName = (TextView) findViewById(R.id.goods_details_name);
        goodsPrice = (TextView) findViewById(R.id.goods_details_price);
        goodsFreight = (TextView) findViewById(R.id.goods_details_freight);
        goodsIntroduce = (WebView) findViewById(R.id.goods_details_introduce);
        balance = (TextView) findViewById(R.id.goods_details_balance);
        buy = (TextView) findViewById(R.id.goods_details_buy);
        countView = (CountNumberView) findViewById(R.id.goods_details_number);
        buy.setOnClickListener(this);
        countView.setOnNumberChangerListener(new CountNumberView.OnNumberChangerListener() {
            @Override
            public void onNumberChange(int curr) {
                if (upgradeGoods.getYf() > 0) {
                    goodsFreight.setText("邮费：¥" + String.valueOf(upgradeGoods.getYf() + 5 * curr) +
                            "(运费首件" + upgradeGoods.getYf() + "元，此后每件依次加" + upgradeGoods.getYf_one() + "元)");
                }
            }
        });
        balance.setText("余额:" + MyApplication.getUserInfo().getYe_price());
        if (goodsId > 0) {
            getGoodsDetails(goodsId);
        }
    }

    private void refresh() {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        volleyUtil.getImage(goodsImage, upgradeGoods.getInfo_file_url());
        goodsName.setText(upgradeGoods.getTitle());
        goodsPrice.setText("单价：¥" + upgradeGoods.getPrice());
        if (upgradeGoods.getYf() <= 0) {
            goodsFreight.setText("免费");
        } else {
            goodsFreight.setText("邮费：¥" + upgradeGoods.getYf() +
                    "(运费首件" + upgradeGoods.getYf() + "元，此后每件依次加" + upgradeGoods.getYf_one() + "元)");
        }
        goodsIntroduce.loadData(HtmlUtil.getNewContent(upgradeGoods.getApp_contents()), "text/html; charset=UTF-8", null);
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
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
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
        if (upgradeGoods.getPrice() * countView.getCurrNum() +
                (upgradeGoods.getYf() <= 0 ? upgradeGoods.getYf() : upgradeGoods.getYf() + 5 * countView.getCurrNum())
                <= MyApplication.getUserInfo().getYe_price()) {
            DialogUtil.showAskMessage(thisContext, "确定要购买吗?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    createUpgradeOrder();
                }
            });
        } else {
            //不足够充值
            DialogUtil.showAskMessage(this, "余额不足,是否立即充值?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivityForResult(new Intent(thisContext, TopUpActivity.class),
                            IntentUtil.REQUEST_FROM_GOODS_DETAILS);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtil.REQUEST_FROM_GOODS_DETAILS
                && resultCode == IntentUtil.RESULT_OK) {
            getUserInfo();
        } else if (requestCode == IntentUtil.REQUEST_FROM_GOODS_DETAILS_INPUT
                && resultCode == IntentUtil.RESULT_OK) {
            pickUp();
        }
    }

    private void createUpgradeOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("goodssj_id", String.valueOf(goodsId));
        map.put("num", String.valueOf(countView.getCurrNum()));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.orderssj(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            OrderInfo_Sup orderInfo_sup =
                                    (OrderInfo_Sup) JsonUtil.stringToObject(response.toString(), OrderInfo_Sup.class);
                            final OrderInfo orderInfo = orderInfo_sup.getRetRes();
                            DialogUtil.createDialog(thisContext, "下单成功", "你需要升级商品吗?", "去提货", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(thisContext, InputAddrActivity.class);
                                    intent.putExtra("position", i);
                                    startActivityForResult(intent, IntentUtil.REQUEST_FROM_GOODS_DETAILS_INPUT);
                                }
                            }, "猜奇偶", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(thisContext, LotteryActivity.class);
                                    intent.putExtra("order_id", orderInfo.getOrders_id());
                                    startActivity(intent);
                                    finish();
                                }
                            });
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
        volleyUtil.addToRequestQueue(request, InternetUtil.orderssj());
    }

    private void getUserInfo() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.userinfo(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            UserInfo_Sup userInfo_sup =
                                    (UserInfo_Sup) JsonUtil.stringToObject(response.toString(), UserInfo_Sup.class);
                            MyApplication.setUserInfo(userInfo_sup.getRetRes());
                            balance.setText("余额:" + MyApplication.getUserInfo().getYe_price());
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
        volleyUtil.addToRequestQueue(request, InternetUtil.userinfo());
    }


    private void pickUp() {
        Map<String, String> map = new HashMap<>();
        map.put("orderssj_id", String.valueOf(upgradeGoods.getId()));
        map.put("title", MyApplication.getUserInfo().getWl_title());
        map.put("phone", MyApplication.getUserInfo().getWl_phone());
        map.put("pca", MyApplication.getUserInfo().getWl_pca());
        map.put("address", MyApplication.getUserInfo().getWl_address());
        map.put("contents", MyApplication.getUserInfo().getWl_content());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjtihuo(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            finish();
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
        volleyUtil.addToRequestQueue(request, InternetUtil.sjtihuo());
    }
}
