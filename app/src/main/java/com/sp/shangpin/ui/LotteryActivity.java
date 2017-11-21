package com.sp.shangpin.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.LineDecoration;
import com.sp.shangpin.adapters.LotteryAdapter;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LotteryInfo;
import com.sp.shangpin.entity.LotteryInfo_Sup;
import com.sp.shangpin.entity.UpgradeOrderInfo;
import com.sp.shangpin.entity.UpgradeOrderPageInfo;
import com.sp.shangpin.entity.UpgradeOrderPageInfo_Sup;
import com.sp.shangpin.utils.CalendarUtil;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/11.
 * 开奖页面
 */

public class LotteryActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private RadioGroup jiAndOu;
    private RecyclerView recyclerView;
    private LotteryAdapter adapter;
    private List<LotteryInfo> data = new ArrayList<>();
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private int orderId;
    private View goodsInfo;
    private int reslut = 1;
    private Button submit;
    private ImageView notUpgradeImage, upgradeImage;
    private TextView notUpgradeName, upgradeName, notUpgradePrice, upgradePrice;
    private UpgradeOrderPageInfo upgradeOrderPageInfo;
    private UpgradeOrderInfo upgradeOrderInfo;
    private TextView nextTime;
    private Timer timer;
    private View bgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        orderId = getIntent().getIntExtra("order_id", -1);
        init();
    }

    private void init() {
        initActionBar();
        initViews();
        initListViews();
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 10000);
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("活动抽奖");
        setSupportActionBar(toolbar);
        setTitle("");
    }

    private void initViews() {
        jiAndOu = (RadioGroup) findViewById(R.id.lottery_ji_ou);
        goodsInfo = findViewById(R.id.lottery_goods_info);
        bgView = findViewById(R.id.lottery_ji_ou_bg);
        if (orderId == -1) {
            goodsInfo.setVisibility(View.GONE);
            bgView.setVisibility(View.GONE);
        } else {
            goodsInfo.setVisibility(View.VISIBLE);
            bgView.setVisibility(View.VISIBLE);
            initJiOu();
        }
    }

    private void initJiOu() {
        submit = (Button) findViewById(R.id.lottery_submit);
        nextTime = (TextView) findViewById(R.id.lottery_next_time);
        notUpgradeImage = (ImageView) findViewById(R.id.fragment_lotto_list_item_not_upgrade_image);
        upgradeImage = (ImageView) findViewById(R.id.fragment_lotto_list_item_upgrade_image);
        notUpgradeName = (TextView) findViewById(R.id.fragment_lotto_list_item_not_upgrade_name);
        upgradeName = (TextView) findViewById(R.id.fragment_lotto_list_item_upgrade_name);
        notUpgradePrice = (TextView) findViewById(R.id.fragment_lotto_list_item_not_upgrade_price);
        upgradePrice = (TextView) findViewById(R.id.fragment_lotto_list_item_upgrade_price);
        jiAndOu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.lottery_ji) {
                    reslut = 1;
                } else if (i == R.id.lottery_ou) {
                    reslut = 2;
                } else {
                    reslut = 1;
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCaiJiOu(reslut);
            }
        });
    }

    private void initListViews() {
        //        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.lottery_swipe_layout);
        recyclerView = (RecyclerView) findViewById(R.id.lottery_list);
        recyclerView.setNestedScrollingEnabled(false);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            public void onRefresh() {
//                getLotteryInfosById();
//            }
//        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(thisContext);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new LotteryAdapter(thisContext, data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LineDecoration(this, LineDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addOnScrollListener(new EndLessOnScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int currentPage) {
//                /**
//                 * 加载更多
//                 */
//                Log.i(TAG, "加载更多" + currentPage);
//            }
//        });
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        if (orderId > -1) {
            VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
            volleyUtil.getImage(notUpgradeImage, upgradeOrderInfo.getGoodssj_file_url());
            volleyUtil.getImage(upgradeImage, upgradeOrderInfo.getGoodssj_file_url_2());
            notUpgradeName.setText(upgradeOrderInfo.getGoodssj_title());
            upgradeName.setText(upgradeOrderInfo.getGoodssj_title_2());
            notUpgradePrice.setText("¥" + upgradeOrderInfo.getDanjia());
            upgradePrice.setText("¥" + upgradeOrderInfo.getGoodssj_price_2());
            goodsInfo.setVisibility(View.VISIBLE);
            nextTime.setText("下次开奖时间:" + new CalendarUtil(upgradeOrderPageInfo.getNext_time(), true).format(CalendarUtil.YYYY_MM_DD_HH_MM));
            nextTime.setVisibility(View.VISIBLE);
        }
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

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
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
                            long time = upgradeOrderPageInfo.getNext_time() * 1000;
                            long curr = System.currentTimeMillis();
                            int minute = (int) ((time - curr) / (1000 * 60));
                            DialogUtil.createDialog(thisContext, "提示", "还有" + minute + "分钟开奖", "再逛逛", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }, "坐等开奖", null);
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.sjsj());

    }

    private void getLotteryInfosById() {
        Map<String, String> map = new HashMap<>();
        map.put("orderssj_id", String.valueOf(orderId));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(this);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjdata(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            UpgradeOrderPageInfo_Sup upgradeOrderPageInfo_sup = (UpgradeOrderPageInfo_Sup)
                                    JsonUtil.stringToObject(response.toString(), UpgradeOrderPageInfo_Sup.class);
                            upgradeOrderPageInfo = upgradeOrderPageInfo_sup.getRetRes();
                            data.clear();
                            data.addAll(upgradeOrderPageInfo.getLists());
                            upgradeOrderInfo = upgradeOrderPageInfo.getInfo();
                            refresh();
//                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
//                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
//                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.cpkj());

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
//                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            DialogUtil.showErrorMessage(thisContext, interResult.getRetErr());
//                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(thisContext, error.toString());
//                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.cpkj());

    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (orderId == -1) {
                getLotteryInfos();
            } else {
                getLotteryInfosById();
            }
        }
    }


}

