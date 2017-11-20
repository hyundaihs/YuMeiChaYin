package com.sp.shangpin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.EndLessOnScrollListener;
import com.sp.shangpin.adapters.SpacesItemDecoration;
import com.sp.shangpin.adapters.TopUpLogAdapter;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.TopUpLogInfo;
import com.sp.shangpin.entity.TopUpLogInfo_Sup;
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
 * Created by 蔡雨峰 on 2017/11/20.
 */

public class TopUpLogActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private RecyclerView recyclerView;
    private TopUpLogAdapter adapter;
    private List<TopUpLogInfo> data = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndLessOnScrollListener endLessOnScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_log);
        init();
        initActionBar();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.fragment_paid_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_paid_swipe_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new TopUpLogAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getTopUpLog(false, currentPage);
            }
        };
        recyclerView.addOnScrollListener(endLessOnScrollListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getTopUpLog(true, 1);
                endLessOnScrollListener.setCurrentPage(1);
            }
        });
        getTopUpLog(true, 1);
        endLessOnScrollListener.setCurrentPage(1);
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("充值记录");
        setSupportActionBar(toolbar);
        setTitle("");
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

    private void getTopUpLog(final boolean isRefresh, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.czlists(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            TopUpLogInfo_Sup topUpLogInfo_sup = (TopUpLogInfo_Sup) JsonUtil.stringToObject(response.toString(), TopUpLogInfo_Sup.class);
                            Log.d(TAG, response.toString());
                            if (isRefresh) {
                                data.clear();
                            }
                            data.addAll(topUpLogInfo_sup.getRetRes());
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            DialogUtil.showErrorMessage(TopUpLogActivity.this, interResult.getRetErr());
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(TopUpLogActivity.this, error.toString());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.sjlists());

    }
}
