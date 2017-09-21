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
import com.sp.shangpin.fragments.FragmentYhq;

import java.util.ArrayList;
import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/21.
 */

public class YhqActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;
    private List<BaseFragment> list;
    private String[] titles = {"待使用", "已使用", "已过期"};
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.orders_toolbar);
        TextView textView = (TextView) findViewById(R.id.orders_toolbar_title);
        textView.setText("我的优惠券");
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void initViews() {
        list = new ArrayList<>();
        list.add(FragmentYhq.getInstance(1));
        list.add(FragmentYhq.getInstance(2));
        list.add(FragmentYhq.getInstance(3));
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
