package com.sp.shangpin.fragments;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/12.
 */

public class FragmentReturnGoods extends BaseFragment {
    private static BaseFragment baseFragment;

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentReturnGoods();
        }
        return baseFragment;
    }
}
