package com.sp.shangpin.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentsAdapter;
import com.sp.shangpin.entity.NormalOrderType;
import com.sp.shangpin.fragments.BaseFragment;
import com.sp.shangpin.fragments.FragmentNormalPaid;
import com.sp.shangpin.fragments.FragmentNormalPickUp;
import com.sp.shangpin.fragments.FragmentNormalReturned;

import java.util.ArrayList;
import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalOrdersActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private List<BaseFragment> list;
    private String[] titles = {"已付订单", "退货订单", "提货订单"};
    private int orderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        orderType = getIntent().getIntExtra(NormalOrderType.KEY, NormalOrderType.GOLD);
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.orders_toolbar);
        TextView textView = (TextView) findViewById(R.id.orders_toolbar_title);
        switch (orderType){
            case NormalOrderType.GOLD:
                textView.setText("金币商品订单");
                break;
            case NormalOrderType.ORIGINAL:
                textView.setText("精品商品订单");
                break;
            case NormalOrderType.ON_SALE:
                textView.setText("促销商品订单");
                break;
        }
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews() {
        list = new ArrayList<>();
        list.add(FragmentNormalPaid.getInstance(orderType));
        list.add(FragmentNormalReturned.getInstance(orderType));
        list.add(FragmentNormalPickUp.getInstance(orderType));
        ViewPager viewPager = (ViewPager) findViewById(R.id.orders_content);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.orders_model_tab_layout);
        //ViewPager的适配器
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
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

