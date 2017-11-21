package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.shangpin.R;

import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/21.
 */

public class StringArrayAdapter extends RecyclerView.Adapter<StringArrayAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;

    public StringArrayAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(FragmentHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(StringArrayAdapter.MyViewHolder holder, final int position) {
        holder.text.setText(mDatas.get(position)+ "张");
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public StringArrayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.simple_list_item_1, parent, false);
        return new StringArrayAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        MyViewHolder(View view) {
            super(view);
            text = (TextView) view;
        }
    }
}

