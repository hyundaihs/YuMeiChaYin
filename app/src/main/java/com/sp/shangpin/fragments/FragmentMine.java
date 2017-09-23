package com.sp.shangpin.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MainActivity;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.adapters.FragmentMineAdapter;
import com.sp.shangpin.utils.IntentUtil;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.NormalOrderType;
import com.sp.shangpin.entity.RealNameInfo;
import com.sp.shangpin.entity.RealNameInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.UserInfo_Sup;
import com.sp.shangpin.ui.AlertPasswordActivity;
import com.sp.shangpin.ui.GetCashActivity;
import com.sp.shangpin.ui.NormalGoodsActivity;
import com.sp.shangpin.ui.NormalOrdersActivity;
import com.sp.shangpin.ui.OrdersActivity;
import com.sp.shangpin.ui.RealNameActivity;
import com.sp.shangpin.ui.TopUpActivity;
import com.sp.shangpin.ui.YhqActivity;
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
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/4.
 */

public class FragmentMine extends BaseFragment {
    private static BaseFragment baseFragment;
    private final String TAG = getClass().getSimpleName();
    private ImageView imageBg, imagePhone;
    private TextView balance;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FragmentMineAdapter adapter;
    private String[] MENUS = {"充值", "提现", "金币专区", "升级产品订单",
            "金币商品订单", "精品商品订单", "促销商品订单", "修改密码", "我的代金券", "实名认证", "实名认证状态"};

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
        toolbar = view.findViewById(R.id.fragment_mine_toolbar);
        imageBg = view.findViewById(R.id.fragment_mine_image_bg);
        imagePhone = view.findViewById(R.id.fragment_mine_image_photo);
        balance = view.findViewById(R.id.fragment_mine_image_balance);
        recyclerView = view.findViewById(R.id.fragment_mine_recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
        initListView();
        refresh();
        getUserInfo();
    }

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new FragmentMineAdapter(getActivity(), MENUS);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new FragmentHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0://充值
                        startActivityForResult(new Intent(getActivity(), TopUpActivity.class),
                                IntentUtil.REQUEST_FROM_FRAGMENT_MINE);
                        break;
                    case 1://提现
                        startActivity(new Intent(getActivity(), GetCashActivity.class));
                        break;
                    case 2://金币专区
                        Intent intent = new Intent(getActivity(), NormalGoodsActivity.class);
                        intent.putExtra(NormalOrderType.KEY, NormalOrderType.GOLD);
                        startActivity(intent);
                        break;
                    case 3://升级产品订单
                        startActivity(new Intent(getActivity(), OrdersActivity.class));
                        break;
                    case 4://金币商品订单
                        intent = new Intent(getActivity(), NormalOrdersActivity.class);
                        intent.putExtra(NormalOrderType.KEY, NormalOrderType.GOLD);
                        startActivity(intent);
                        break;
                    case 5://精品商品订单
                        intent = new Intent(getActivity(), NormalOrdersActivity.class);
                        intent.putExtra(NormalOrderType.KEY, NormalOrderType.ORIGINAL);
                        startActivity(intent);
                        break;
                    case 6://促销商品订单
                        intent = new Intent(getActivity(), NormalOrdersActivity.class);
                        intent.putExtra(NormalOrderType.KEY, NormalOrderType.ON_SALE);
                        startActivity(intent);
                        break;
                    case 7://修改密码
                        intent = new Intent(getActivity(), AlertPasswordActivity.class);
                        startActivityForResult(intent, IntentUtil.REQUEST_FROM_MINE_TO_ALERT_PASS);
                        break;
                    case 8://我的代金券
                        startActivity(new Intent(getActivity(), YhqActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(getActivity(), RealNameActivity.class));
                        break;
                    case 10:
                        getRealNameStatus();
                        break;
                }
            }
        });
    }

    public void initActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_mine);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_mine, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mine_login_out:
                DialogUtil.showAskMessage(getActivity(), "确定要退出登录吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtil.REQUEST_FROM_FRAGMENT_MINE
                && resultCode == IntentUtil.RESULT_OK) {
            getUserInfo();
        } else if (requestCode == IntentUtil.REQUEST_FROM_MINE_TO_ALERT_PASS
                && resultCode == IntentUtil.RESULT_OK) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    private void refresh() {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        if (null != MyApplication.userInfo) {
            volleyUtil.getImage(imagePhone, MyApplication.userInfo.getFile_url());
            balance.setText("账户余额:¥" + MyApplication.userInfo.getYe_price()
                    + "\n账户金币:" + MyApplication.userInfo.getJf_price());
        }
    }

    private void getUserInfo() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
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
                            DialogUtil.showErrorMessage(getActivity(), interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(getActivity(), error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.userinfo());
    }

    private void getRealNameStatus() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.smrzstatus(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            RealNameInfo_Sup realNameInfo_sup =
                                    (RealNameInfo_Sup) JsonUtil.stringToObject(response.toString(), RealNameInfo_Sup.class);
                            RealNameInfo realNameInfo = realNameInfo_sup.getRetRes();
                            String status = realNameInfo.getStatus() == 1 ? "审核中" : realNameInfo.getStatus() == 2 ? "通过" : "拒绝";
                            String text = "真实姓名：" + realNameInfo.getTrue_name() +
                                    " 性别：" + realNameInfo.getSex() +
                                    "\n身份证：" + realNameInfo.getId_numbers() +
                                    "\n审核状态：" + status;
                            DialogUtil.showTipMessage(getActivity(), text, null);
                        } else {
                            DialogUtil.showErrorMessage(getActivity(), interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(getActivity(), error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.userinfo());
    }

    private void logout() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.logout(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            SharedPreferencesUtil.setParam(getActivity(), SharedKey.IS_REMEMBER, false);
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            DialogUtil.showErrorMessage(getActivity(), interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(getActivity(), error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.logout());
    }
}
