package com.sp.shangpin.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.RequestAndResult;
import com.sp.shangpin.entity.UserInfo_Sup;
import com.sp.shangpin.ui.NormalOrdersActivity;
import com.sp.shangpin.ui.OrdersActivity;
import com.sp.shangpin.ui.TopUpActivity;
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
 * Created by 蔡雨峰 on 2017/9/4.
 */

public class FragmentMine extends BaseFragment implements View.OnClickListener {

    private static BaseFragment baseFragment;
    private ImageView imageBg, imagePhone;
    private TextView balance;
    private Context thisContext = getActivity();

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentMine();
        }
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        imageBg = view.findViewById(R.id.fragment_mine_image_bg);
        imagePhone = view.findViewById(R.id.fragment_mine_image_photo);
        balance = view.findViewById(R.id.fragment_mine_image_balance);
        view.findViewById(R.id.fragment_mine_recharge).setOnClickListener(this);
        view.findViewById(R.id.fragment_mine_cash).setOnClickListener(this);
        view.findViewById(R.id.fragment_mine_gold_coin).setOnClickListener(this);
        view.findViewById(R.id.fragment_mine_upgrade_orders).setOnClickListener(this);
        view.findViewById(R.id.fragment_mine_orders).setOnClickListener(this);
        view.findViewById(R.id.fragment_mine_normal_orders).setOnClickListener(this);
        view.findViewById(R.id.fragment_mine_alert_pwd).setOnClickListener(this);
        view.findViewById(R.id.fragment_mine_voucher).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
        getUserInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_mine_recharge:
                startActivityForResult(new Intent(thisContext, TopUpActivity.class),
                        RequestAndResult.REQUEST_FROM_FRAGMENT_MINE);
                break;
            case R.id.fragment_mine_cash:
                break;
            case R.id.fragment_mine_gold_coin:
                break;
            case R.id.fragment_mine_upgrade_orders:
                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                break;
            case R.id.fragment_mine_normal_orders:
                startActivity(new Intent(getActivity(), NormalOrdersActivity.class));
                break;
            case R.id.fragment_mine_orders:
                startActivity(new Intent(getActivity(), OrdersActivity.class));
                break;
            case R.id.fragment_mine_alert_pwd:
                break;
            case R.id.fragment_mine_voucher:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestAndResult.REQUEST_FROM_FRAGMENT_MINE
                && resultCode == RequestAndResult.RESULT_OK) {
            getUserInfo();
        }
    }

    private void refresh() {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        if (null != MyApplication.userInfo) {
            volleyUtil.getImage(imagePhone, MyApplication.userInfo.getFile_url());
            balance.setText("账户余额:￥" + MyApplication.userInfo.getYe_price()
                    + "\n账户金币:" + MyApplication.userInfo.getJf_price());
        }
    }

    private void getUserInfo() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
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
}
