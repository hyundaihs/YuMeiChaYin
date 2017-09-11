package com.sp.shangpin.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.EndLessOnScrollListener;
import com.sp.shangpin.adapters.LineDecoration;
import com.sp.shangpin.adapters.LotteryAdapter;
import com.sp.shangpin.entity.LotteryInfo;
import com.sp.shangpin.entity.LotteryInfo_Sup;
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
 * Created by 蔡雨峰 on 2017/9/11.
 * 开奖页面
 */

public class LotteryActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private TextView ji, ou;
    private RecyclerView recyclerView;
    private LotteryAdapter adapter;
    private List<LotteryInfo> data = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        init();
    }

    private void init() {
        initActionBar();
        initViews();
        initListViews();
        getLotteryInfos();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.lottery_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void initViews() {
        ji = (TextView) findViewById(R.id.lottery_ji);
        ou = (TextView) findViewById(R.id.lottery_ou);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.lottery_swipe_layout);
        recyclerView = (RecyclerView) findViewById(R.id.lottery_list);
        ji.setOnClickListener(this);
        ou.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getLotteryInfos();
            }
        });
    }

    private void initListViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(thisContext);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new LotteryAdapter(thisContext, data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LineDecoration(this, LineDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                /**
                 * 加载更多
                 */
                Log.i(TAG, "加载更多" + currentPage);
            }
        });
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
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

    private void getLotteryInfos() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.cpkj(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LotteryInfo_Sup lotteryInfo_sup = (LotteryInfo_Sup)
                                JsonUtil.stringToObject(response.toString(), LotteryInfo_Sup.class);
                        if (lotteryInfo_sup.isSuccessed()) {
                            data.clear();
                            data.addAll(lotteryInfo_sup.getRetRes());
                            refresh();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            DialogUtil.showErrorMessage(thisContext, lotteryInfo_sup.getRetErr());
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_up_top_up:
                setResult(12);
                finish();
                break;
            case R.id.top_up_price1:
                break;
        }
    }

}

