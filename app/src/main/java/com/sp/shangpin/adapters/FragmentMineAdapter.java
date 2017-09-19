package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.shangpin.R;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/19.
 */

public class FragmentMineAdapter extends RecyclerView.Adapter<FragmentMineAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private String[] mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;

    public FragmentMineAdapter(Context context, String[] datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(FragmentHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.length;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(FragmentMineAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(mDatas[position]);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public FragmentMineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_mine_list_item, parent, false);
        return new FragmentMineAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        View rootView;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            title = view.findViewById(R.id.fragment_mine_list_item_title);
        }
    }
}
