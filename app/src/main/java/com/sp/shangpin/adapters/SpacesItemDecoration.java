package com.sp.shangpin.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/15.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int colum;

    public SpacesItemDecoration(int space, int colum) {
        this.colum = colum;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int count = parent.getAdapter().getItemCount();
        int rows = count / colum + count % colum;
        int firstInLastrow = (rows - 1) * colum;
        int position = parent.getChildLayoutPosition(view);
        //不是第一个的格子都设一个上边和右边的间距
        outRect.top = space;
        outRect.right = space;
        outRect.left = 0;
        outRect.bottom = 0;
        //左边的设一个左边距
        if (position % colum == 0) {
            outRect.left = space;
        }
        //最后一排设下边距
        if (position >= firstInLastrow) {
            outRect.bottom = space;
        }
    }
}

