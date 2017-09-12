package com.sp.shangpin.fragments;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/12.
 */

public class FragmentPickUp extends BaseFragment {

    private static BaseFragment baseFragment;

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentPickUp();
        }
        return baseFragment;
    }
}
