package com.sp.shangpin.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.sp.shangpin.R;
import com.sp.shangpin.utils.DisplayUtil;

/**
 * NestedScrollingDemo
 * Created by 蔡雨峰 on 2017/9/14.
 */

public class MyNestedScrollView extends LinearLayout implements NestedScrollingParent {
    private int TOP_CHILD_FLING_THRESHOLD = 3;
    private View mTop;
    private View mNav;
    private RecyclerView mViewPager;
    private int mTopViewHeight;
    private int mViewPagerHeight;
    private int maxScrollHeight;
    private OverScroller mScroller;
    private ValueAnimator mOffsetAnimator;
    private int topId = 0, middleId = 0, listChildId = 0;
    private int colum = 1;
    private int viewPagerFirstHeight = 0;

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyNestedScrollView);
        topId = ta.getResourceId(R.styleable.MyNestedScrollView_NestedTop, 0);
        middleId = ta.getResourceId(R.styleable.MyNestedScrollView_NestedMiddle, 0);
        listChildId = ta.getResourceId(R.styleable.MyNestedScrollView_NestedList, 0);
        colum = ta.getInt(R.styleable.MyNestedScrollView_NestedColum, 1);
        ta.recycle();
        mScroller = new OverScroller(context);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
    }

    @Override
    public void onStopNestedScroll(View target) {
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < maxScrollHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0), consumed);
        } else {
            animateScroll(velocityY, computeDuration(velocityY), consumed);
        }
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //不做拦截 可以传递给子View
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    /**
     * 根据速度计算滚动动画持续时间
     *
     * @param velocityY
     * @return
     */
    private int computeDuration(float velocityY) {
        final int distance;
        if (velocityY > 0) {
            distance = Math.abs(mTop.getHeight() - getScrollY());
        } else {
            distance = Math.abs(mTop.getHeight() - (mTop.getHeight() - getScrollY()));
        }


        final int duration;
        velocityY = Math.abs(velocityY);
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY));
        } else {
            final float distanceRatio = (float) distance / getHeight();
            duration = (int) ((distanceRatio + 1) * 150);
        }

        return duration;

    }

    private void animateScroll(float velocityY, final int duration, boolean consumed) {
        final int currentOffset = getScrollY();
        final int topHeight = mTop.getHeight();
        if (mOffsetAnimator == null) {
            mOffsetAnimator = new ValueAnimator();
//            mOffsetAnimator.setInterpolator(mInterpolator);
            mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        scrollTo(0, (Integer) animation.getAnimatedValue());
                    }
                }
            });
        } else {
            mOffsetAnimator.cancel();
        }
        mOffsetAnimator.setDuration(Math.min(duration, 600));

        if (velocityY >= 0) {
            mOffsetAnimator.setIntValues(currentOffset, topHeight);
            mOffsetAnimator.start();
        } else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if (!consumed) {
                mOffsetAnimator.setIntValues(currentOffset, 0);
                mOffsetAnimator.start();
            }

        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(topId);
        mNav = findViewById(middleId);
        View view = findViewById(listChildId);
        if (!(view instanceof RecyclerView)) {
            throw new RuntimeException(
                    "id_stickynavlayout_viewpager show used by RecyclerView !");
        }
        mViewPager = (RecyclerView) view;
    }

    private int viewHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(viewHeight == 0){
            viewHeight = getMeasuredHeight();
        }
        int count = mViewPager.getChildCount();
        if (count > 0) {
            View view = mViewPager.getChildAt(0);
            int row = count / colum + count % colum;
            int height = row * view.getMeasuredHeight() + DisplayUtil.dp2px(getContext(), 5) * (row + 1);
            ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
            if (viewPagerFirstHeight == 0) {
                viewPagerFirstHeight = mViewPager.getMeasuredHeight();
            }
            int height2 = viewHeight - (mNav != null ? mNav.getMeasuredHeight() : 0);
            params.height = height > height2 ? height2 : height;
            measureChild(mViewPager, widthMeasureSpec, heightMeasureSpec);
        }
        mViewPagerHeight = mViewPager.getMeasuredHeight();
        maxScrollHeight = mViewPagerHeight - viewPagerFirstHeight;
        maxScrollHeight = maxScrollHeight < mTopViewHeight ? maxScrollHeight : mTopViewHeight;
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + mViewPager.getMeasuredHeight() +
                (mNav != null ? mNav.getMeasuredHeight() : 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
    }


    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, maxScrollHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > maxScrollHeight) {
            y = maxScrollHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }


}
