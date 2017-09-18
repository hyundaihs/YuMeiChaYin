package com.sp.shangpin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.EndLessOnScrollListener;
import com.sp.shangpin.adapters.LineDecoration;
import com.sp.shangpin.adapters.NormalOrdersAdapter;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.NormalOrderInfo;
import com.sp.shangpin.entity.NormalOrderInfo_Sup;
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
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class FragmentNormalReturned extends BaseFragment  {
    private static BaseFragment baseFragment;

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentNormalReturned();
        }
        return baseFragment;
    }

    private RecyclerView recyclerView;
    private NormalOrdersAdapter adapter;
    private List<NormalOrderInfo> data = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndLessOnScrollListener endLessOnScrollListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal_paid, container, false);
        recyclerView = view.findViewById(R.id.fragment_normal_paid_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.fragment_normal_paid_swipe_layout);
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
        adapter = new NormalOrdersAdapter(getActivity(), this, data, 1);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LineDecoration(getActivity(), LineDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getNormalOrdersInfo(false, currentPage);
            }
        };
        recyclerView.addOnScrollListener(endLessOnScrollListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getNormalOrdersInfo(true, 1);
                endLessOnScrollListener.setCurrentPage(1);
            }
        });
        getNormalOrdersInfo(true, 1);
        endLessOnScrollListener.setCurrentPage(1);
    }

    private void getNormalOrdersInfo(final boolean isRefresh, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("pagesize", "20");
        map.put("type_id", "2");
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.orderslists(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            NormalOrderInfo_Sup normalOrderInfo_sup = (NormalOrderInfo_Sup) JsonUtil.stringToObject(
                                    response.toString(), NormalOrderInfo_Sup.class);
                            if (isRefresh) {
                                data.clear();
                            }
                            data.addAll(normalOrderInfo_sup.getRetRes());
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

}

