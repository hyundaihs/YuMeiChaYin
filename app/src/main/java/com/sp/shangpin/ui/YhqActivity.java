package com.sp.shangpin.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentsAdapter;
import com.sp.shangpin.utils.IntentUtil;
import com.sp.shangpin.fragments.BaseFragment;
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
    private int index, what, max;
    private TextView button;
    private boolean isCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        index = getIntent().getIntExtra("index", 0);
        what = getIntent().getIntExtra("what", 0);
        max = getIntent().getIntExtra("max", 1);
        isCheck = what == IntentUtil.REQUEST_FROM_NORMAL_GOODS_DETAILS_YHQ;
        initActionBar();
        initViews();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        button = (TextView) findViewById(R.id.toolbar_btn);
        title.setText("我的优惠券");
        setSupportActionBar(toolbar);
        setTitle("");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("ids", ((FragmentYhq) list.get(0)).getCheckIds());
                setResult(IntentUtil.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initViews() {

        list = new ArrayList<>();
        list.add(FragmentYhq.getInstance(1, isCheck, max));
        list.add(FragmentYhq.getInstance(2, isCheck, max));
        list.add(FragmentYhq.getInstance(3, isCheck, max));
        ViewPager viewPager = (ViewPager) findViewById(R.id.orders_content);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.orders_model_tab_layout);
        //ViewPager的适配器
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(index);
        button.setVisibility(isCheck ? View.VISIBLE : View.GONE);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                button.setVisibility(position == 0 && isCheck ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
