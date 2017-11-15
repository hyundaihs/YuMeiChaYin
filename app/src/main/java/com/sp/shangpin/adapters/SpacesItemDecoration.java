package com.sp.shangpin.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
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

    /**
     * 单列并且只设置上边距,第一个不要
     *
     * @param space
     */
    public SpacesItemDecoration(int space) {
        this.space = space;
        this.colum = 0;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (colum == 0) {
            int position = parent.getChildLayoutPosition(view);
            outRect.right = 0;
            outRect.left = 0;
            outRect.bottom = 0;
            if (position == 0) {//第一个不要
                outRect.top = 0;
            } else {
                outRect.top = space;
            }
        } else {
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
}

