package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.entity.TopUpLogInfo;
import com.sp.shangpin.utils.CalendarUtil;

import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/20.
 */

public class TopUpLogAdapter extends RecyclerView.Adapter<TopUpLogAdapter.MyViewHolder> {

    private final String TAG = getClass().getSimpleName();
    private List<TopUpLogInfo> mDatas;
    private LayoutInflater inflater;

    public TopUpLogAdapter(Context context, List<TopUpLogInfo> datas) {
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public TopUpLogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_top_up_log_list_item, parent, false);
        return new TopUpLogAdapter.MyViewHolder(view);
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(TopUpLogAdapter.MyViewHolder holder, final int position) {
        TopUpLogInfo topUpLogInfo = mDatas.get(position);
        holder.type.setText(topUpLogInfo.getPay_type().equals("weixin") ? "微信" :
                (topUpLogInfo.getPay_type().equals("alipay") ? "支付宝" : "银联"));
        holder.price.setText(String.valueOf(topUpLogInfo.getPrice()));
        //1待支付，2已支付，3已取消
        holder.status.setText(topUpLogInfo.getStatus() == 1 ? "待支付" :
                (topUpLogInfo.getStatus() == 2 ? "已支付" : "已取消"));
        CalendarUtil calendarUtil = new CalendarUtil(topUpLogInfo.getCreate_time(),true);
        holder.time.setText(calendarUtil.format(CalendarUtil.STANDARD_));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type, price, status, time;

        MyViewHolder(View view) {
            super(view);
            type = view.findViewById(R.id.top_up_log_item_type);
            price = view.findViewById(R.id.top_up_log_item_price);
            status = view.findViewById(R.id.top_up_log_item_status);
            time = view.findViewById(R.id.top_up_log_item_time);
        }
    }
}
