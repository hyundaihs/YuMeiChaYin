package com.sp.shangpin.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sp.shangpin.fragments.FragmentLotto;

import java.util.List;

/**
 * Created by kevin on 2017/9/4.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
//        if (position == 1) {
//            fragment = new FragmentLotto();
//        } else {
//            fragment = fragments.get(position);
//        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
