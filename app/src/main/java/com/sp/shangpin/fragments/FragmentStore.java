package com.sp.shangpin.fragments;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.NormalOrderType;
import com.sp.shangpin.ui.NormalGoodsActivity;
import com.sp.shangpin.ui.RuleActivity;
import com.sp.shangpin.utils.VolleyUtil;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/4.
 */

public class FragmentStore extends BaseFragment implements View.OnClickListener {
    private static BaseFragment baseFragment;

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
        final View content = getView().findViewById(R.id.fragment_store_content);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        volleyUtil.getBitmap(MyApplication.getSystemInfo().getSjsc_file_url(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(response.getBitmap());
                content.setBackground(bitmapDrawable);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
//        Toolbar toolbar = getView().findViewById(R.id.toolbar);
//        TextView title = getView().findViewById(R.id.toolbar_title);
//        TextView btn = getView().findViewById(R.id.toolbar_btn);
//        title.setText("商城");
//        btn.setText("规则");
//        btn.setVisibility(View.VISIBLE);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        toolbar.setTitle("");
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), RuleActivity.class));
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_store_store1://精品商品
                Intent intent = new Intent(getActivity(), NormalGoodsActivity.class);
                intent.putExtra(NormalOrderType.KEY, NormalOrderType.ORIGINAL);
                startActivity(intent);
                break;
            case R.id.fragment_store_store2://金币商品
                intent = new Intent(getActivity(), NormalGoodsActivity.class);
                intent.putExtra(NormalOrderType.KEY, NormalOrderType.GOLD);
                startActivity(intent);
                break;
            case R.id.fragment_store_store3://促销商品
                intent = new Intent(getActivity(), NormalGoodsActivity.class);
                intent.putExtra(NormalOrderType.KEY, NormalOrderType.ON_SALE);
                startActivity(intent);
                break;
            case R.id.fragment_store_store4:
                break;
        }
    }
}
