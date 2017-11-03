package com.sp.shangpin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.adapters.FragmentLottoAdapter;
import com.sp.shangpin.adapters.SpacesItemDecoration;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LottoInfo;
import com.sp.shangpin.entity.LottoInfo_Sup;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.ui.GoodsDetailsActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.DisplayUtil;
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
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/10/13.
 */

public class FragmentLottoContent extends BaseFragment {
    private static BaseFragment baseFragment;
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private int index = 0;
    private Banner typeImage;
    private LottoInfo lottoInfo;
    private FragmentLottoAdapter adapter;
    private List<UpgradeGoods> data;

    public static BaseFragment getInstance(int index) {
        baseFragment = new FragmentLottoContent();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lotto, container, false);
        typeImage = view.findViewById(R.id.fragment_lotto_type_image);
        recyclerView = view.findViewById(R.id.fragment_lotto_recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        index = getArguments().getInt("index", 1);
        initListView();
        getDataInfo();
    }

    private void getDataInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("type_id", String.valueOf(index));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.goodssj(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            LottoInfo_Sup lottoInfo_sup = (LottoInfo_Sup) JsonUtil.stringToObject(response.toString(), LottoInfo_Sup.class);
                            lottoInfo = lottoInfo_sup.getRetRes();
                            Log.i(TAG, "抽奖信息获取成功" + lottoInfo);
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
        volleyUtil.addToRequestQueue(request, InternetUtil.goodssj());
    }

    private void refresh() {
        if (null != lottoInfo) {
            if (null != lottoInfo.getBanner()) {
                List<String> images = new ArrayList<>();
                for (int i = 0; i < lottoInfo.getBanner().size(); i++) {
                    images.add(lottoInfo.getBanner().get(i).getFile_url());
                }
                typeImage.setImages(images).setImageLoader(new GlideImageLoader()).start();
            }
            if (null != lottoInfo.getLists()) {
                data.clear();
                data.addAll(lottoInfo.getLists());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initListView() {
        data = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new FragmentLottoAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtil.dp2px(getActivity(), 5), 1));
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

}
