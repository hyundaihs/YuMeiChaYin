package com.sp.shangpin.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.sp.shangpin.R;
import com.sp.shangpin.fragments.FragmentGoldCoin;
import com.sp.shangpin.fragments.FragmentPickUp;
import com.sp.shangpin.fragments.FragmentReturnGoods;
import com.sp.shangpin.fragments.FragmentWait;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/12.
 */

public class OrdersActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private RadioGroup radioGroup;

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
        radioGroup = (RadioGroup) findViewById(R.id.orders_model_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.orders_model_flag1:
                        loadFragment(FragmentWait.getInstance());
                        break;
                    case R.id.orders_model_flag2:
                        loadFragment(FragmentReturnGoods.getInstance());
                        break;
                    case R.id.orders_model_flag3:
                        loadFragment(FragmentPickUp.getInstance());
                        break;
                    case R.id.orders_model_flag4:
                        loadFragment(FragmentGoldCoin.getInstance());
                        break;
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.orders_content, fragment);
        ft.commit();
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

