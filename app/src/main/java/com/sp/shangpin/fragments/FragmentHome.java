package com.sp.shangpin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.adapters.SpacesItemDecoration;
import com.sp.shangpin.entity.HomeInfo;
import com.sp.shangpin.entity.HomeInfo_Sup;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.NotificationInfo;
import com.sp.shangpin.entity.NotificationInfo_Sup;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.ui.GoodsDetailsActivity;
import com.sp.shangpin.ui.HomeActivity;
import com.sp.shangpin.ui.RuleActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.DisplayUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.ToastUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.GlideImageLoader;
import com.youth.banner.Banner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/4.
 * 首页
 */

public class FragmentHome extends BaseFragment implements View.OnClickListener {
    private static BaseFragment baseFragment;
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private Banner banner;
    private HomeInfo homeInfo;
    //    private ImageView[] imageViews = new ImageView[3];
//    private TextView[] textViews = new TextView[3];
    private List<UpgradeGoods> data;
    private FragmentHomeAdapter adapter;
    private NotificationInfo_Sup notificationInfo_sup = new NotificationInfo_Sup();
    private List<NotificationInfo> notificationInfo = new ArrayList<>();
    private int index = 0;
    private Timer timer;

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
        banner = view.findViewById(R.id.fragment_home_banner);
        view.findViewById(R.id.fragment_home_upgrade1).setOnClickListener(this);
        view.findViewById(R.id.fragment_home_upgrade2).setOnClickListener(this);
        view.findViewById(R.id.fragment_home_upgrade3).setOnClickListener(this);
        recyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
        initListView();
        getHomeInfo();
        getNotification();
    }

    private void getNotification() {
        Map<String, String> map = new HashMap<>();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.tz(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            notificationInfo_sup = (NotificationInfo_Sup) JsonUtil.stringToObject(response.toString(), NotificationInfo_Sup.class);
                        } else {
                            DialogUtil.showErrorMessage(getActivity(), interResult.getRetErr());
                        }
                    }
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(getActivity(), error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.indexdata());
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 5000, 5000);
    }

    @Override
    public void onStop() {
        timer.cancel();
        super.onStop();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (index == notificationInfo.size()) {
                index = 0;
                if (null != notificationInfo_sup.getRetRes()) {
                    notificationInfo = notificationInfo_sup.getRetRes();
                }
            }
            if (notificationInfo.size() > 0 && index < notificationInfo.size()) {
                ToastUtil.show(getActivity(), notificationInfo.get(index).getTitle(), notificationInfo.get(index).getFile_url(), 1);
                index++;
                if (index == notificationInfo.size() - 2) {
                    getNotification();
                }
            }
        }
    };

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
                }, new RequestUtil.MyErrorListener() {
                    @Override
                    public void onErrorResponse(String error) {
                        DialogUtil.showErrorMessage(getActivity(), error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.indexdata());
    }

    private void refresh() {
        initBanner();
//        initViews();
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

//    private void initViews() {
//        if (null != homeInfo && null != homeInfo.getSjfl()) {
//            for (int i = 0; i < homeInfo.getSjfl().size(); i++) {
//                if (i >= imageViews.length) {
//                    continue;
//                }
//                UpgradeGoodsType upgradeGoodsType = homeInfo.getSjfl().get(i);
//                VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
//                volleyUtil.getImage(imageViews[i], upgradeGoodsType.getFile_url());
//                textViews[i].setText(upgradeGoodsType.getTitle());
//                imageViews[i].setOnClickListener(this);
//            }
//        }
//    }

    private void initListView() {
        data = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new FragmentHomeAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtil.dp2px(getActivity(), 5), 2));
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
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        TextView title = getView().findViewById(R.id.toolbar_title);
        TextView btn = getView().findViewById(R.id.toolbar_btn);
        title.setText("首页");
        btn.setText("规则");
        btn.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle("");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RuleActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_home_upgrade1:
                ((HomeActivity) getActivity()).checkTab(1, 0);
                break;
            case R.id.fragment_home_upgrade2:
                ((HomeActivity) getActivity()).checkTab(1, 1);
                break;
            case R.id.fragment_home_upgrade3:
                ((HomeActivity) getActivity()).checkTab(1, 2);
                break;
        }
    }
}
