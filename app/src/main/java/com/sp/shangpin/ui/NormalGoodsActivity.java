package com.sp.shangpin.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.adapters.NormalGoodsAdapter;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.NormalGoodsInfo;
import com.sp.shangpin.entity.NormalGoodsInfoList;
import com.sp.shangpin.entity.NormalGoodsInfoList_Sup;
import com.sp.shangpin.entity.NormalOrderType;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.GlideImageLoader;
import com.youth.banner.Banner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private RecyclerView recyclerView;
    private Banner banner;
    private List<String> images;
    private List<NormalGoodsInfo> data;
    private NormalGoodsAdapter adapter;
    private int orderType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_goods);
        orderType = getIntent().getIntExtra(NormalOrderType.KEY, NormalOrderType.ORIGINAL);
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.normal_goods_toolbar);
        TextView title = (TextView) findViewById(R.id.normal_goods_toolbar_title);
        switch (orderType) {
            case NormalOrderType.ORIGINAL:
                title.setText("精品商城");
                break;
            case NormalOrderType.GOLD:
                title.setText("金币商城");
                break;
            case NormalOrderType.ON_SALE:
                title.setText("促销商城");
                break;
        }
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.normal_goods_recyclerView);
        banner = (Banner) findViewById(R.id.normal_goods_banner);
        images = new ArrayList<>();
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        data = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(thisContext, 2);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new NormalGoodsAdapter(thisContext, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new FragmentHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(thisContext, NormalGoodsDetailsActivity.class);
                intent.putExtra("id", data.get(position).getId());
                intent.putExtra(NormalOrderType.KEY, orderType);
                startActivity(intent);
            }
        });
        getGoodsInfo(4);
    }

    private void refresh() {
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        adapter.notifyDataSetChanged();
    }

    private void getGoodsInfo(int yhq_num) {
        Map<String, String> map = new HashMap<>();
        map.put("type_id", "");//type_id:类别ID
        map.put("cx", orderType == NormalOrderType.ON_SALE ? "1" : "0");//cx:是否促销（0：原价商城，1：促销商城）
        map.put("yhq_num", String.valueOf(yhq_num));//yhq_num:优惠券数量
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(orderType == NormalOrderType.GOLD
                        ? InternetUtil.goodsjf() : InternetUtil.goods(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            NormalGoodsInfoList_Sup normalGoodsInfoList_sup = (NormalGoodsInfoList_Sup) JsonUtil.stringToObject(response.toString(),
                                    NormalGoodsInfoList_Sup.class);
                            NormalGoodsInfoList normalGoodsInfoList = normalGoodsInfoList_sup.getRetRes();
                            images.clear();
                            for (int i = 0; i < normalGoodsInfoList.getInfo().size(); i++) {
                                images.add(normalGoodsInfoList.getInfo().get(i).getBanner_file_url());
                            }
                            data.clear();
                            data.addAll(normalGoodsInfoList.getLists());
                            refresh();
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
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
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
}
