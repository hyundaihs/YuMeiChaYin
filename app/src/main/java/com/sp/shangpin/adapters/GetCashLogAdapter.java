package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.entity.GetCashInfo;

import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/14.
 */

public class GetCashLogAdapter extends RecyclerView.Adapter<GetCashLogAdapter.MyViewHolder> {

    private final String TAG = getClass().getSimpleName();
    private List<GetCashInfo> mDatas;
    private LayoutInflater inflater;

    public GetCashLogAdapter(Context context, List<GetCashInfo> datas) {
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public GetCashLogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.orders_list_item, parent, false);
        return new GetCashLogAdapter.MyViewHolder(view);
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(GetCashLogAdapter.MyViewHolder holder, final int position) {
        GetCashInfo getCashInfo = mDatas.get(position);
        holder.time.setText("时间:" + getCashInfo.getUpdate_time());
        holder.type.setText("提现类型:" + (getCashInfo.getType_id() == 1 ? "银行卡" : "微信"));
        holder.money.setText("金额:" + getCashInfo.getPrice());
        holder.name.setText("姓名:" + getCashInfo.getTitle());
        holder.phone.setText("电话:" + getCashInfo.getPhone());
        holder.bankName.setText("开户行:" + getCashInfo.getBank_name());
        holder.bankNum.setText("银行卡号:" + getCashInfo.getCard_numbers());
        holder.beizhu.setText("备注:" + getCashInfo.getContents());
        holder.status.setText(getCashInfo.getStatus() == 1 ? "未处理" : (getCashInfo.getStatus() == 2 ? "已打款" : "拒绝打款"));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView time, type, money, name, phone, bankName, bankNum, beizhu, status;

        MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.cash_log_time);
            type = view.findViewById(R.id.cash_log_type);
            money = view.findViewById(R.id.cash_log_money);
            name = view.findViewById(R.id.cash_log_name);
            phone = view.findViewById(R.id.cash_log_phone);
            bankName = view.findViewById(R.id.cash_log_bank_name);
            bankNum = view.findViewById(R.id.cash_log_bank_num);
            beizhu = view.findViewById(R.id.cash_log_beizhu);
            status = view.findViewById(R.id.cash_log_status);
        }
    }
}
