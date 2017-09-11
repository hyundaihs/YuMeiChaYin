package com.sp.shangpin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sp.shangpin.R;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/4.
 */

public class FragmentMine extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine,container,false);
    }
}
