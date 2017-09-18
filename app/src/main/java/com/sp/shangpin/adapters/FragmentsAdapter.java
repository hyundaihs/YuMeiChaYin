package com.sp.shangpin.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sp.shangpin.fragments.BaseFragment;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class FragmentsAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;
    private String[] titles;

    public FragmentsAdapter(FragmentManager fm, List<BaseFragment> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
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
        return position < titles.length ? titles[position] : "";
    }
}
