package com.sp.shangpin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.entity.LotteryInfo;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/11.
 */

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<LotteryInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;

    public LotteryAdapter(Context context, List<LotteryInfo> datas) {
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
    public void onBindViewHolder(LotteryAdapter.MyViewHolder holder, final int position) {
        LotteryInfo lotteryInfo = mDatas.get(position);
        holder.date.setText(lotteryInfo.getDates());
        holder.numbers.setText(lotteryInfo.getNumbers());
        holder.codes.setText(lotteryInfo.getCodes());
        holder.jiou.setText(lotteryInfo.getJo() == 1 ? "奇" : "偶");
        holder.jiou.setBackgroundResource(lotteryInfo.getJo() == 1 ? R.drawable.maroon_corner : R.drawable.orange_corner);
        holder.rootView.setBackgroundResource(lotteryInfo.getJo() == 1 ? R.color.fragment_content_bg : android.R.color.white);
        if (null != onItemClickListener) {
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public LotteryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lottery_list_item, parent, false);
        return new LotteryAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, numbers, codes, jiou;
        View rootView;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            date = view.findViewById(R.id.lottery_list_item_date);
            numbers = view.findViewById(R.id.lottery_list_item_numbers);
            codes = view.findViewById(R.id.lottery_list_item_codes);
            jiou = view.findViewById(R.id.lottery_list_item_jiou);
            date.setTextColor(Color.BLACK);
            numbers.setTextColor(Color.BLACK);
            codes.setTextColor(Color.BLACK);
        }
    }
}