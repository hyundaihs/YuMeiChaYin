package com.sp.shangpin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.EndLessOnScrollListener;
import com.sp.shangpin.adapters.LineDecoration;
import com.sp.shangpin.adapters.OrdersAdapter;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.OrderInfo;
import com.sp.shangpin.entity.OrdersInfo_Sup;
import com.sp.shangpin.entity.IntentUtil;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/12.
 */

public class FragmentUpgrade extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private static BaseFragment baseFragment;

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentUpgrade();
        }
        return baseFragment;
    }

    private RecyclerView recyclerView;
    private OrdersAdapter adapter;
    private List<OrderInfo> data = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndLessOnScrollListener endLessOnScrollListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paid, container, false);
        recyclerView = view.findViewById(R.id.fragment_paid_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.fragment_paid_swipe_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListViews();
    }

    private void initListViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new OrdersAdapter(getActivity(),this, data,1);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LineDecoration(getActivity(), LineDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getOrdersInfo(false, currentPage);
            }
        };
        recyclerView.addOnScrollListener(endLessOnScrollListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getOrdersInfo(true, 1);
                endLessOnScrollListener.setCurrentPage(1);
            }
        });
        getOrdersInfo(true, 1);
        endLessOnScrollListener.setCurrentPage(1);
    }

    private void getOrdersInfo(final boolean isRefresh, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("pagesize", "20");
        map.put("type_id", "2");
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjlists(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            OrdersInfo_Sup ordersInfo_sup = (OrdersInfo_Sup) JsonUtil.stringToObject(response.toString(), OrdersInfo_Sup.class);
                            if (isRefresh) {
                                data.clear();
                            }
                            data.addAll(ordersInfo_sup.getRetRes());
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
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
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Paid", "onActivityResult");
        if (requestCode == IntentUtil.REQUEST_FROM_PAID && resultCode == IntentUtil.RESULT_OK) {
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                pickUp(position);
            }
        }
    }

    private void pickUp(final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("orderssj_id", String.valueOf(data.get(position).getId()));
        map.put("title", MyApplication.userInfo.getWl_title());
        map.put("phone", MyApplication.userInfo.getWl_phone());
        map.put("pca", MyApplication.userInfo.getWl_pca());
        map.put("address", MyApplication.userInfo.getWl_address());
        map.put("contents", MyApplication.userInfo.getWl_content());
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjtihuo(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            data.remove(position);
                            adapter.notifyItemRemoved(position);
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
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
    }
}
