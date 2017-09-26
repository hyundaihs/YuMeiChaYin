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
import com.sp.shangpin.fragments.BaseFragment;
import com.sp.shangpin.fragments.FragmentPaid;
import com.sp.shangpin.fragments.FragmentPickUp;
import com.sp.shangpin.fragments.FragmentReturned;
import com.sp.shangpin.fragments.FragmentUpgrade;

import java.util.ArrayList;
import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/12.
 */

public class OrdersActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;
    private List<BaseFragment> list;
    private String[] titles = {"已付订单", "升级产品", "退货订单", "提货订单"};
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        index = getIntent().getIntExtra("index", 0);
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(R.string.setting_orders);
        setSupportActionBar(toolbar);
        setTitle("");

    }

    private void initViews() {
        list = new ArrayList<>();
        list.add(FragmentPaid.getInstance());
        list.add(FragmentUpgrade.getInstance());
        list.add(FragmentReturned.getInstance());
        list.add(FragmentPickUp.getInstance());
        ViewPager viewPager = (ViewPager) findViewById(R.id.orders_content);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.orders_model_tab_layout);
        //ViewPager的适配器
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(index);
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

