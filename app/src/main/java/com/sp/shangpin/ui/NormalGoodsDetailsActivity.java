package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.NormalGoodsInfo;
import com.sp.shangpin.entity.NormalGoodsInfo_Sup;
import com.sp.shangpin.entity.NormalOrderType;
import com.sp.shangpin.entity.OrderInfo_Sup;
import com.sp.shangpin.entity.UserInfo_Sup;
import com.sp.shangpin.entity.YHQ;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.IntentUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.CountNumberView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private NormalGoodsInfo normalGoodsInfo;
    private ImageView goodsImage;
    private TextView goodsName, goodsPrice, goodsFreight,  yuanPrice;
    private WebView goodsIntroduce;
    private TextView balance, buy;
    private CountNumberView countView;
    private TextView yhq;
    private View yhqLayout;
    private int goodsId;
    private int orderType;
    private ArrayList<YHQ> checkYhq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        orderType = getIntent().getIntExtra(NormalOrderType.KEY, NormalOrderType.ORIGINAL);
        checkYhq = new ArrayList<>();
        init();
    }

    private void init() {
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        switch (orderType) {
            case NormalOrderType.ORIGINAL:
                title.setText("精品商品详情");
                break;
            case NormalOrderType.GOLD:
                title.setText("金币商品详情");
                break;
            case NormalOrderType.ON_SALE:
                title.setText("促销商品详情");
                break;
        }
        setSupportActionBar(toolbar);
        setTitle("");


    }

    private void initViews() {
        goodsId = getIntent().getIntExtra("id", -1);
        goodsImage = (ImageView) findViewById(R.id.goods_details_image);
        goodsName = (TextView) findViewById(R.id.goods_details_name);
        goodsPrice = (TextView) findViewById(R.id.goods_details_price);
        yuanPrice = (TextView) findViewById(R.id.goods_details_yuan_price);
        goodsFreight = (TextView) findViewById(R.id.goods_details_freight);
        goodsIntroduce = (WebView) findViewById(R.id.goods_details_introduce);
        balance = (TextView) findViewById(R.id.goods_details_balance);
        buy = (TextView) findViewById(R.id.goods_details_buy);
        countView = (CountNumberView) findViewById(R.id.goods_details_number);
        yhqLayout = findViewById(R.id.goods_details_yhq_layout);
        yhq = (TextView) findViewById(R.id.goods_details_yhq);
        yhqLayout.setVisibility(orderType == NormalOrderType.ORIGINAL ? View.VISIBLE : View.GONE);
        buy.setOnClickListener(this);
        yhqLayout.setOnClickListener(this);
        countView.setOnNumberChangerListener(new CountNumberView.OnNumberChangerListener() {
            @Override
            public void onNumberChange(int curr) {
                goodsFreight.setText("邮费：¥" + String.valueOf(normalGoodsInfo.getYf() + 5 * curr) +
                        "(运费首件" + normalGoodsInfo.getYf() + "元，此后每件依次加" + normalGoodsInfo.getYf_one() + "元)");
            }
        });
        setBalance();
        if (goodsId > 0) {
            getGoodsDetails(goodsId);
        }
        getUserInfo();
    }

    private void setBalance() {
        if (orderType == NormalOrderType.GOLD) {
            balance.setText("金币:" + MyApplication.userInfo.getJf_price());
        } else {
            balance.setText("余额:" + MyApplication.userInfo.getYe_price());
        }
    }

    private void refresh() {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        volleyUtil.getImage(goodsImage, normalGoodsInfo.getInfo_file_url());
        goodsName.setText(normalGoodsInfo.getTitle());
        goodsPrice.setText("单价：¥" + normalGoodsInfo.getPrice());
        goodsFreight.setText("邮费：¥" + normalGoodsInfo.getYf() +
                "(运费首件" + normalGoodsInfo.getYf() + "元，此后每件依次加" + normalGoodsInfo.getYf_one() + "元)");
        if (null != normalGoodsInfo.getApp_contents()) {
            goodsIntroduce.loadData(normalGoodsInfo.getApp_contents(), "text/html; charset=UTF-8", null);
        }
        if (orderType == NormalOrderType.ORIGINAL) {
            yhq.setText("您有" + normalGoodsInfo.getYhq_num() + "张优惠券可以使用,点击使用");
        }
        if (normalGoodsInfo.getCx() == 1) {
            yuanPrice.setVisibility(View.VISIBLE);
            yuanPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            yuanPrice.setText("原价：¥" + normalGoodsInfo.getYuanjia());
        }
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
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(orderType == NormalOrderType.GOLD
                        ? InternetUtil.goodsjfinfo() : InternetUtil.goodsinfo(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            NormalGoodsInfo_Sup normalGoodsInfo_sup = (NormalGoodsInfo_Sup) JsonUtil.stringToObject
                                    (response.toString(), NormalGoodsInfo_Sup.class);
                            normalGoodsInfo = normalGoodsInfo_sup.getRetRes();
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
        volleyUtil.addToRequestQueue(request, orderType == NormalOrderType.GOLD
                ? InternetUtil.goodsjfinfo() : InternetUtil.goodsinfo());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_details_buy:
                buyPay();
                break;
            case R.id.goods_details_yhq_layout:
                Intent intent = new Intent(this, YhqActivity.class);
                intent.putExtra("what", IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS_YHQ);
                intent.putExtra("max", normalGoodsInfo.getYhq_num());
                startActivityForResult(intent, IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS_YHQ);
                break;
        }
    }

    private void buyPay() {
        double youhui = 0;
        for (int i = 0; i < checkYhq.size(); i++) {
            youhui += checkYhq.get(i).getPrice();
        }
        double yufei = normalGoodsInfo.getYf() + 5 * (countView.getCurrNum() - 1);
        double total = normalGoodsInfo.getPrice() * countView.getCurrNum() +
                normalGoodsInfo.getYf() + 5 * countView.getCurrNum() - youhui;
        if (total <= MyApplication.userInfo.getYe_price()) {
            DialogUtil.showAskMessage(thisContext, "您将购买此商品" + countView.getCurrNum() +
                    "件,商品总价:¥" + normalGoodsInfo.getPrice() * countView.getCurrNum() +
                    ",物流费:¥" + yufei +
                    ",优惠:¥" + youhui + "\n合计:¥" + total, "去提货", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(thisContext, InputAddrActivity.class);
                    intent.putExtra("position", i);
                    startActivityForResult(intent, IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS_INPUT);
                }
            });
        } else {
            //不足够充值
            DialogUtil.showAskMessage(this, "余额不足,是否立即充值?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivityForResult(new Intent(thisContext, TopUpActivity.class),
                            IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS
                && resultCode == IntentUtil.RESULT_OK) {
            getUserInfo();
        } else if (requestCode == IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS_INPUT
                && resultCode == IntentUtil.RESULT_OK) {
            createNormalOrder();
        } else if (requestCode == IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS_YHQ
                && resultCode == IntentUtil.RESULT_OK) {
            checkYhq.clear();
            ArrayList<YHQ> ids = data.getParcelableArrayListExtra("ids");
            checkYhq.addAll(ids);
            yhq.setText("优惠券:");
            for (int i = 0; i < ids.size(); i++) {
                yhq.append("-" + ids.get(i).getPrice() + " ");
            }
        }
    }

    private void createNormalOrder() {
        Map<String, Object> map = new HashMap<>();
        map.put(orderType == NormalOrderType.GOLD ? "goodsjf_id" : "goods_id", String.valueOf(goodsId)); // goods_id:产品ID
        map.put("num", String.valueOf(countView.getCurrNum())); // num:数量
        map.put("title", MyApplication.userInfo.getWl_title()); // title:收货人 张三
        map.put("phone", MyApplication.userInfo.getWl_phone()); // phone：电话 18614221874
        map.put("pca", MyApplication.userInfo.getWl_pca()); // pca：收货地区 湖北省武汉市江夏区
        map.put("address", MyApplication.userInfo.getWl_address()); // address:详细地址 武大科技园
        map.put("contents", MyApplication.userInfo.getWl_content()); // contents：备注
        if (orderType == NormalOrderType.ORIGINAL) {
            int[] array = new int[checkYhq.size()];
            for (int i = 0; i < checkYhq.size(); i++) {
                array[i] = (checkYhq.get(i).getId());
            }
            map.put("yhq_ids", array); // yhq_ids:优惠券ID（数组）
        }
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(orderType == NormalOrderType.GOLD
                        ? InternetUtil.ordersjf() : InternetUtil.orders(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            OrderInfo_Sup orderInfo_sup =
                                    (OrderInfo_Sup) JsonUtil.stringToObject(response.toString(), OrderInfo_Sup.class);
                            finish();
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
        volleyUtil.addToRequestQueue(request, orderType == NormalOrderType.GOLD
                ? InternetUtil.ordersjf() : InternetUtil.orders());
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
                            MyApplication.userInfo = userInfo_sup.getRetRes();
                            setBalance();
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
        volleyUtil.addToRequestQueue(request, InternetUtil.userinfo());
    }
}
