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
import com.sp.shangpin.adapters.FragmentYhqAdapter;
import com.sp.shangpin.adapters.SpacesItemDecoration;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.YHQ;
import com.sp.shangpin.entity.YHQS_Sup;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.DisplayUtil;
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
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/21.
 */

public class FragmentYhq extends BaseFragment {
    private static BaseFragment baseFragment;
    private static boolean isCheck;
    private static int maxNum;
    private final String TAG = getClass().getSimpleName();
    private int status;
    private RecyclerView recyclerView;
    private FragmentYhqAdapter adapter;
    private List<YHQ> data = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    public static BaseFragment getInstance(int status, boolean check, int max) {
        baseFragment = new FragmentYhq();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        baseFragment.setArguments(bundle);
        isCheck = check;
        maxNum = max;
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paid, container, false);
        recyclerView = view.findViewById(R.id.fragment_paid_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.fragment_paid_swipe_layout);
        status = getArguments().getInt("status", 1);
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
        adapter = new FragmentYhqAdapter(getActivity(), data, isCheck, maxNum);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtil.dp2px(getActivity(), 10), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getYHQInfo();
            }
        });
        getYHQInfo();
    }

    public ArrayList<YHQ> getCheckIds() {
        return adapter.getCheckedIds();
    }

    private void getYHQInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("status", String.valueOf(status));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.yhq(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            YHQS_Sup yhqs_sup = (YHQS_Sup) JsonUtil.stringToObject(response.toString()
                                    , YHQS_Sup.class);
                            data.clear();
                            data.addAll(yhqs_sup.getRetRes());
                            adapter.cleanChecked();
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
        volleyUtil.addToRequestQueue(request, InternetUtil.yhq());
    }


}
