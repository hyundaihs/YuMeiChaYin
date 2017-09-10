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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.entity.HomeInfo;
import com.sp.shangpin.entity.HomeInfo_Sup;
import com.sp.shangpin.entity.LoginInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.UpgradeGoodsType;
import com.sp.shangpin.ui.GoodsDetailsActivity;
import com.sp.shangpin.ui.HomeActivity;
import com.sp.shangpin.ui.LoginActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.sp.shangpin.widget.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 2017/9/4.
 */

public class FragmentHome extends BaseFragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    private RecyclerView recyclerView;
    private HomeInfo homeInfo;
    private ImageView[] imageViews = new ImageView[3];
    private TextView[] textViews = new TextView[3];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
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
                        HomeInfo_Sup homeInfo_sup = (HomeInfo_Sup) JsonUtil.stringToObject(response.toString(), HomeInfo_Sup.class);
                        if (homeInfo_sup.isSuccessed()) {
                            homeInfo = homeInfo_sup.getRetRes();
                            initBanner();
                            initViews();
                            initListView();
                        } else {
                            DialogUtil.showErrorMessage(getActivity(), homeInfo_sup.getRetErr());
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

    private void initBanner() {
        if (null != homeInfo && null != homeInfo.getBanner()) {
            List<String> images = new ArrayList<>();
            for (int i = 0; i < homeInfo.getBanner().length; i++) {
                images.add(homeInfo.getBanner()[i].getFile_url());
            }
            Banner banner = getView().findViewById(R.id.fragment_home_banner);
            banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        }
    }

    private void initViews() {
        if (null != homeInfo && null != homeInfo.getSjfl()) {
            imageViews[0] = getView().findViewById(R.id.fragment_home_upgrade1);
            imageViews[1] = getView().findViewById(R.id.fragment_home_upgrade2);
            imageViews[2] = getView().findViewById(R.id.fragment_home_upgrade3);
            textViews[0] = getView().findViewById(R.id.fragment_home_upgrade1_title);
            textViews[1] = getView().findViewById(R.id.fragment_home_upgrade2_title);
            textViews[2] = getView().findViewById(R.id.fragment_home_upgrade3_title);
            for (int i = 0; i < homeInfo.getSjfl().length; i++) {
                if (i >= imageViews.length) {
                    continue;
                }
                UpgradeGoodsType upgradeGoodsType = homeInfo.getSjfl()[i];
                VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
                volleyUtil.getImage(imageViews[i], upgradeGoodsType.getFile_url());
                textViews[i].setText(upgradeGoodsType.getTitle());
                imageViews[i].setOnClickListener(this);
            }
        }
    }

    private void initListView() {
        if (null != homeInfo && null != homeInfo.getSjcp()) {
            recyclerView = getView().findViewById(R.id.fragment_home_recyclerView);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            FragmentHomeAdapter adapter = new FragmentHomeAdapter(getActivity(), homeInfo.getSjcp());
            recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.setOnItemClickListener(new FragmentHomeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                    intent.putExtra("id", homeInfo.getSjcp()[position].getId());
                    startActivity(intent);
                }
            });
        }
    }

    public void initActionBar() {
        Toolbar toolbar = getView().findViewById(R.id.fragment_home_toolbar);
        toolbar.inflateMenu(R.menu.menu_home);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_rule:
                Toast.makeText(getActivity(), "规则", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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
