package com.sp.shangpin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sp.shangpin.R;
import com.sp.shangpin.ui.NormalGoodsActivity;
import com.sp.shangpin.ui.RuleActivity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/4.
 */

public class FragmentStore extends BaseFragment implements View.OnClickListener {
    private static BaseFragment baseFragment;
    private Toolbar toolbar;

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentStore();
        }
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        toolbar = view.findViewById(R.id.fragment_store_toolbar);
        view.findViewById(R.id.fragment_store_store1).setOnClickListener(this);
        view.findViewById(R.id.fragment_store_store2).setOnClickListener(this);
        view.findViewById(R.id.fragment_store_store3).setOnClickListener(this);
        view.findViewById(R.id.fragment_store_store4).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
    }

    public void initActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_store);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_store, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_store_rule:
                startActivity(new Intent(getActivity(), RuleActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_store_store1:
                startActivity(new Intent(getActivity(), NormalGoodsActivity.class));
                break;
            case R.id.fragment_store_store2:
                break;
            case R.id.fragment_store_store3:
                break;
            case R.id.fragment_store_store4:
                break;
        }
    }
}
