package com.sp.shangpin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentsAdapter;
import com.sp.shangpin.ui.RuleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/4.
 * 抽奖
 */

public class FragmentLotto extends BaseFragment {
    private static BaseFragment baseFragment;
    private final String TAG = getClass().getSimpleName();
    private List<BaseFragment> list;
    private String[] titles = {"茶类型", "红酒类型", "精品类型"};
    private int index;

    public static BaseFragment getInstance(int index) {
        if (index >= 0) {
            baseFragment = new FragmentLotto();
            Bundle bundle = new Bundle();
            bundle.putInt("index", index);
            baseFragment.setArguments(bundle);
        }
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_orders, container, false);
        initActionBar(view);
        initViews(view);
        index = getArguments().getInt("index", 0);
        Log.d(TAG, "获取index" + index);
        return view;
    }

    private void initViews(View view) {
        list = new ArrayList<>();
        list.add(FragmentLottoContent.getInstance(1));
        list.add(FragmentLottoContent.getInstance(2));
        list.add(FragmentLottoContent.getInstance(3));
        ViewPager viewPager = view.findViewById(R.id.orders_content);
        TabLayout tabLayout = view.findViewById(R.id.orders_model_tab_layout);
        //ViewPager的适配器
        FragmentsAdapter adapter = new FragmentsAdapter(getChildFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(index).select();
//        viewPager.setCurrentItem(index);
    }

    public void initActionBar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView title = view.findViewById(R.id.toolbar_title);
        TextView btn = view.findViewById(R.id.toolbar_btn);
        title.setText("升级商品");
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

}
