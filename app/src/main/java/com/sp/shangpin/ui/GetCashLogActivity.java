package com.sp.shangpin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.EndLessOnScrollListener;
import com.sp.shangpin.adapters.GetCashLogAdapter;
import com.sp.shangpin.adapters.SpacesItemDecoration;
import com.sp.shangpin.entity.GetCashInfo;
import com.sp.shangpin.entity.GetCashInfo_Sup;
import com.sp.shangpin.entity.InterResult;
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
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/14.
 */

public class GetCashLogActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private RecyclerView recyclerView;
    private GetCashLogAdapter adapter;
    private List<GetCashInfo> data = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndLessOnScrollListener endLessOnScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_paid);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.fragment_paid_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_paid_swipe_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new GetCashLogAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getCashInfoLog(false, currentPage);
            }
        };
        recyclerView.addOnScrollListener(endLessOnScrollListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getCashInfoLog(true, 1);
                endLessOnScrollListener.setCurrentPage(1);
            }
        });
        getCashInfoLog(true, 1);
        endLessOnScrollListener.setCurrentPage(1);
    }

    private void getCashInfoLog(final boolean isRefresh, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.txlists(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            GetCashInfo_Sup getCashInfo_sup = (GetCashInfo_Sup) JsonUtil.stringToObject(response.toString(), GetCashInfo_Sup.class);
                            if (isRefresh) {
                                data.clear();
                            }
                            data.addAll(getCashInfo_sup.getRetRes());
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            DialogUtil.showErrorMessage(GetCashLogActivity.this, interResult.getRetErr());
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(GetCashLogActivity.this, error.toString());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.sjlists());
    }
}
