package com.sp.shangpin.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.sp.shangpin.R;
import com.sp.shangpin.fragments.BaseFragment;
import com.sp.shangpin.fragments.FragmentReturned;
import com.sp.shangpin.fragments.FragmentPickUp;
import com.sp.shangpin.fragments.FragmentUpgrade;
import com.sp.shangpin.fragments.FragmentPayed;

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
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyAdapter adapter;
    private String[] titles = {"已付订单", "升级产品", "退货订单", "提货订单"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.orders_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void initViews() {
        list = new ArrayList<>();
        list.add(FragmentPayed.getInstance());
        list.add(FragmentUpgrade.getInstance());
        list.add(FragmentReturned.getInstance());
        list.add(FragmentPickUp.getInstance());
        viewPager = (ViewPager) findViewById(R.id.orders_content);
        tabLayout = (TabLayout) findViewById(R.id.orders_model_tab_layout);
        //ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager());
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

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}

