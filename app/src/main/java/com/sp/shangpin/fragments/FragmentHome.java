package com.sp.shangpin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.entity.HomeInfo;
import com.sp.shangpin.entity.HomeInfo_Sup;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.entity.UpgradeGoodsType;
import com.sp.shangpin.ui.GoodsDetailsActivity;
import com.sp.shangpin.ui.HomeActivity;
import com.sp.shangpin.ui.RuleActivity;
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
 * Created by 蔡雨峰 on 2017/9/4.
 * 首页
 */

public class FragmentHome extends BaseFragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    private RecyclerView recyclerView;
    private Banner banner;
    private Toolbar toolbar;
    private HomeInfo homeInfo;
    private ImageView[] imageViews = new ImageView[3];
    private TextView[] textViews = new TextView[3];
    private List<UpgradeGoods> data;
    private FragmentHomeAdapter adapter;

    private static BaseFragment baseFragment;

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentHome();
        }
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.fragment_home_toolbar);
        banner = view.findViewById(R.id.fragment_home_banner);
        imageViews[0] = view.findViewById(R.id.fragment_home_upgrade1);
        imageViews[1] = view.findViewById(R.id.fragment_home_upgrade2);
        imageViews[2] = view.findViewById(R.id.fragment_home_upgrade3);
        textViews[0] = view.findViewById(R.id.fragment_home_upgrade1_title);
        textViews[1] = view.findViewById(R.id.fragment_home_upgrade2_title);
        textViews[2] = view.findViewById(R.id.fragment_home_upgrade3_title);
        recyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
        initListView();
        getHomeInfo();
    }

    private void getHomeInfo() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.indexdata(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                        HomeInfo_Sup homeInfo_sup = (HomeInfo_Sup) JsonUtil.stringToObject(response.toString(), HomeInfo_Sup.class);
                            Log.i(TAG, "首页信息获取成功");
                            homeInfo = homeInfo_sup.getRetRes();
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
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
    }

    private void refresh() {
        initBanner();
        initViews();
        if (null != homeInfo && null != homeInfo.getSjcp()) {
            data.clear();
            data.addAll(homeInfo.getSjcp());
            adapter.notifyDataSetChanged();
        }
    }

    private void initBanner() {
        if (null != homeInfo && null != homeInfo.getBanner()) {
            List<String> images = new ArrayList<>();
            for (int i = 0; i < homeInfo.getBanner().size(); i++) {
                images.add(homeInfo.getBanner().get(i).getFile_url());
            }
            banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        }
    }

    private void initViews() {
        if (null != homeInfo && null != homeInfo.getSjfl()) {
            for (int i = 0; i < homeInfo.getSjfl().size(); i++) {
                if (i >= imageViews.length) {
                    continue;
                }
                UpgradeGoodsType upgradeGoodsType = homeInfo.getSjfl().get(i);
                VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
                volleyUtil.getImage(imageViews[i], upgradeGoodsType.getFile_url());
                textViews[i].setText(upgradeGoodsType.getTitle());
                imageViews[i].setOnClickListener(this);
            }
        }
    }

    private void initListView() {
        data = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new FragmentHomeAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new FragmentHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                intent.putExtra("id", data.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void initActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_home);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu");
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.menu_home_rule:
                startActivity(new Intent(getActivity(), RuleActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_home_upgrade1:
                MyApplication.lottoCurrIndex = 1;
                ((HomeActivity) getActivity()).checkTab(1);
                break;
            case R.id.fragment_home_upgrade2:
                MyApplication.lottoCurrIndex = 2;
                ((HomeActivity) getActivity()).checkTab(1);
                break;
            case R.id.fragment_home_upgrade3:
                MyApplication.lottoCurrIndex = 3;
                ((HomeActivity) getActivity()).checkTab(1);
                break;
        }
    }
}
