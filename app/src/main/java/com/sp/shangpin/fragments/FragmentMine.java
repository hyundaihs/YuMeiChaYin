package com.sp.shangpin.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.NormalOrderType;
import com.sp.shangpin.entity.RealNameInfo;
import com.sp.shangpin.entity.RealNameInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.UserInfo_Sup;
import com.sp.shangpin.ui.AlertPasswordActivity;
import com.sp.shangpin.ui.GetCashActivity;
import com.sp.shangpin.ui.NormalOrdersActivity;
import com.sp.shangpin.ui.OrdersActivity;
import com.sp.shangpin.ui.RealNameActivity;
import com.sp.shangpin.ui.TopUpActivity;
import com.sp.shangpin.ui.YhqActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.IntentUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.ToastUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
    private RecyclerView recyclerView;
    private FragmentMineAdapter adapter;
    private String[] MENUS = {"充值", "提现", "升级产品订单",
            "金币商品订单", "精品商品订单", "促销商品订单", "修改密码", "我的代金券", "实名认证", "升级方式",
//            "我的返佣提现",
            "分享"};
    private int[] IDS = {R.mipmap.ids_top_up, R.mipmap.ids_get_cash, R.mipmap.ids_upgrade_orders, R.mipmap.ids_gold_orders,
            R.mipmap.ids_well_orders, R.mipmap.ids_on_sale_orders, R.mipmap.ids_alert_password, R.mipmap.ids_my_yhq,
            R.mipmap.ids_real_name, R.mipmap.ids_upgrade_type,
//            R.mipmap.ids_my_get_cash,
            R.mipmap.ids_share};

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
        recyclerView = view.findViewById(R.id.fragment_mine_recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
        initListView();
        refresh();
    }

    @Override
    public void onResume() {
        getUserInfo();
        super.onResume();
    }

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new FragmentMineAdapter(getActivity(), MENUS, IDS);
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
                    case 2://升级产品订单
                        startActivity(new Intent(getActivity(), OrdersActivity.class));
                        break;
                    case 3://金币商品订单
                        Intent intent = new Intent(getActivity(), NormalOrdersActivity.class);
                        intent.putExtra(NormalOrderType.KEY, NormalOrderType.GOLD);
                        startActivity(intent);
                        break;
                    case 4://精品商品订单
                        intent = new Intent(getActivity(), NormalOrdersActivity.class);
                        intent.putExtra(NormalOrderType.KEY, NormalOrderType.ORIGINAL);
                        startActivity(intent);
                        break;
                    case 5://促销商品订单
                        intent = new Intent(getActivity(), NormalOrdersActivity.class);
                        intent.putExtra(NormalOrderType.KEY, NormalOrderType.ON_SALE);
                        startActivity(intent);
                        break;
                    case 6://修改密码
                        intent = new Intent(getActivity(), AlertPasswordActivity.class);
                        startActivityForResult(intent, IntentUtil.REQUEST_FROM_MINE_TO_ALERT_PASS);
                        break;
                    case 7://我的代金券
                        startActivity(new Intent(getActivity(), YhqActivity.class));
                        break;
                    case 8://实名认证
                        getRealNameStatus();
                        break;
                    case 9://升级方式

                        break;
//                    case 10://返佣提现
//
//                        break;
                    case 10://分享
                        DialogUtil.createShareDialog(getActivity(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.share_friend_are:
                                        shareToWx(true);
                                        break;
                                    case R.id.share_wx_friend:
                                        shareToWx(false);
                                        break;
                                    case R.id.share_weibo:
                                        ToastUtil.show(getActivity(), "分享到微博");
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    private void shareToWx(boolean isFriendArea) {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = "https://fir.im/sd4w";
        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
        wxMediaMessage.mediaObject = wxWebpageObject;
        wxMediaMessage.title = "茗品雅汇";
        wxMediaMessage.description = "我们不仅仅是商城，更是游戏把玩的福地，购买即可中大奖！";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon)
                .compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] datas = baos.toByteArray();
        wxMediaMessage.thumbData = datas;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = isFriendArea ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        MyApplication.api.sendReq(req);
    }

    public void initActionBar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        TextView title = getView().findViewById(R.id.toolbar_title);
        TextView btn = getView().findViewById(R.id.toolbar_btn);
        title.setText("我的");
        btn.setText("注销");
        btn.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle("");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showAskMessage(getActivity(), "确定要退出登录吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                    }
                });
            }
        });
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
            Log.d("image", MyApplication.userInfo.getFile_url());
            volleyUtil.getImageByIntact(imagePhone, MyApplication.userInfo.getFile_url());
            String text = "金币：" + MyApplication.userInfo.getJf_price() +
                    " 余额：" + MyApplication.userInfo.getYe_price();
            balance.setText(text);
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
                            DialogUtil.showAskMessage(getActivity(), text, realNameInfo.getStatus() == 3 ? "重新认证" : "", realNameInfo.getStatus() == 3 ? new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getActivity(), RealNameActivity.class));
                                }
                            } : null);
                        } else {
                            DialogUtil.showAskMessage(getActivity(), "您还没有进行过认证.", "去认证", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getActivity(), RealNameActivity.class));
                                }
                            });
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
