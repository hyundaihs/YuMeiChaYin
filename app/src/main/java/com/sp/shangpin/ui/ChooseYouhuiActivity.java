package com.sp.shangpin.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.adapters.LineDecoration;
import com.sp.shangpin.adapters.StringArrayAdapter;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.utils.DisplayUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/21.
 */

public class ChooseYouhuiActivity extends AppCompatActivity {

    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private Context mContext = this;
    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundColor(mContext.getResources().getColor(R.color.ji_bg))
                    .setText("删除") // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(DisplayUtil.dp2px(mContext, 100))
                    .setHeight(MATCH_PARENT);
            rightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };
    private StringArrayAdapter adapter;
    private int max;
    private List<String> mData = new ArrayList<>();
    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            //int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            //int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            mData.remove(adapterPosition);
            adapter.notifyDataSetChanged();
            saveStrings();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhui);
        initActionBar();
        initData();
        init();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        TextView btn = (TextView) findViewById(R.id.toolbar_btn);
        title.setText("代金券可用数量");
        setSupportActionBar(toolbar);
        setTitle("");
        btn.setText("添加");
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int last = Integer.valueOf(mData.get(mData.size() - 1));
                mData.add(String.valueOf(last + 1));
                adapter.notifyItemChanged(mData.size() - 1);
                saveStrings();
            }
        });
    }

    private void saveStrings() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            if (i == 0) {
                sb.append(mData.get(i));
            } else {
                sb.append("," + mData.get(i));
            }
        }
        SharedPreferencesUtil.setParam(this, SharedKey.YOUHUI, sb.toString());
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

    private void initData() {
        String youhuis = (String) SharedPreferencesUtil.getParam(this, SharedKey.YOUHUI, "");
        if (youhuis.equals("")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i <= 5; i++) {
                if (i == 1) {
                    sb.append(String.valueOf(i));
                } else {
                    sb.append("," + String.valueOf(i));
                }
            }
            youhuis = sb.toString();
        }
        String[] strings = youhuis.split(",");
        for (int i = 0; i < strings.length; i++) {
            mData.add(strings[i]);
        }
        saveStrings();
    }


    private void init() {

        swipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.youhui_recyclerView);
        // 设置监听器。
        swipeMenuRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeMenuRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new StringArrayAdapter(this, mData);
        swipeMenuRecyclerView.setAdapter(adapter);
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeMenuRecyclerView.addItemDecoration(new LineDecoration(this, LineDecoration.VERTICAL));
        adapter.setOnItemClickListener(new FragmentHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("choose", Integer.valueOf(mData.get(position)));
                setResult(0, intent);
                finish();
            }
        });

    }

}
