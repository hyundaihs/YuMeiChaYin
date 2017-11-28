package com.sp.shangpin.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.AlipayOrder_Sup;
import com.sp.shangpin.entity.AuthResult;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.PayResult;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.WxPayOrder;
import com.sp.shangpin.entity.WxPayOrder_Sup;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

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

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("充值");
        setSupportActionBar(toolbar);
        setTitle("");


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
        final String type = getPayType();
        map.put("price", getPrice());
        map.put("pay_type", type);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.cz(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            WxPayOrder_Sup wxPayOrder_sup = (WxPayOrder_Sup) JsonUtil.stringToObject(response.toString(), WxPayOrder_Sup.class);
                            if (type.equals(SharedKey.WEIXIN)) {
                                pay(wxPayOrder_sup.getRetRes().getOrders_id());
                            } else if (type.equals(SharedKey.ALIPAY)) {
                                alipay(wxPayOrder_sup.getRetRes().getOrders_id());
                            } else {

                            }
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
        volleyUtil.addToRequestQueue(request, InternetUtil.cz());

    }

    private void pay(int orders_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(orders_id));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.pay(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            WxPayOrder_Sup wxPayOrder_sup = (WxPayOrder_Sup) JsonUtil.stringToObject(response.toString(), WxPayOrder_Sup.class);
                            wxPay(wxPayOrder_sup.getRetRes());
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(thisContext, error);
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.pay());
    }

    private void alipay(int orders_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(orders_id));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.alipay(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (null != interResult && interResult.isSuccessed()) {
                            AlipayOrder_Sup alipayOrder_sup = (AlipayOrder_Sup) JsonUtil.stringToObject(response.toString(), AlipayOrder_Sup.class);
                            alipay(alipayOrder_sup.getRetRes().getStr());
                        } else {
                            assert interResult != null;
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(thisContext, error);
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.pay());
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(TopUpActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(TopUpActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(TopUpActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(TopUpActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    private void alipay(final String orderInfo){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(TopUpActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 阿里授权业务
     * @param authInfo
     */
    private void aliAuth(final String authInfo){
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(TopUpActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取阿里SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    private void wxPay(WxPayOrder wxPayOrder) {
        PayReq request = new PayReq();
        request.appId = InternetUtil.getWChatAppId();
        request.partnerId = wxPayOrder.getPartnerid();
        request.prepayId = wxPayOrder.getPrepayid();
        request.packageValue = wxPayOrder.getPackages();
        request.nonceStr = wxPayOrder.getNoncestr();
        request.timeStamp = wxPayOrder.getTimestamp();
        request.sign = wxPayOrder.getSign();
        MyApplication.getApi().sendReq(request);
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
