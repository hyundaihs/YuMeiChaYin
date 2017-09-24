package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
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
import com.sp.shangpin.entity.InterResult;
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
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        orderId = getIntent().getIntExtra("order_id", 0);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCaiJiOu(int index) {
        Map<String, String> map = new HashMap<>();
        map.put("orderssj_id", String.valueOf(orderId));
        map.put("jo", String.valueOf(index));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjsj(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            DialogUtil.createDialog(thisContext, "提示", "还有多久开奖", "再逛逛", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }, "坐等开奖", null);
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
        volleyUtil.addToRequestQueue(request, InternetUtil.sjsj());

    }

    private void getLotteryInfos() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.cpkj(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            LotteryInfo_Sup lotteryInfo_sup = (LotteryInfo_Sup)
                                    JsonUtil.stringToObject(response.toString(), LotteryInfo_Sup.class);
                            data.clear();
                            data.addAll(lotteryInfo_sup.getRetRes());
                            refresh();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
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
        volleyUtil.addToRequestQueue(request, InternetUtil.cpkj());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lottery_ji:
                DialogUtil.showAskMessage(thisContext, "确定选择\"奇\"吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getCaiJiOu(1);
                    }
                });
                break;
            case R.id.lottery_ou:
                DialogUtil.showAskMessage(thisContext, "确定选择\"偶\"吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getCaiJiOu(2);
                    }
                });
                break;
        }
    }

}

