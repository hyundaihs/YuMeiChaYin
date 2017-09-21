package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.entity.YHQ;

import java.util.ArrayList;
import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/21.
 */

public class FragmentYhqAdapter extends RecyclerView.Adapter<FragmentYhqAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<YHQ> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;
    private List<Integer> checked;

    public FragmentYhqAdapter(Context context, List<YHQ> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        checked = new ArrayList<>();
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
    public void onBindViewHolder(FragmentYhqAdapter.MyViewHolder holder, final int position) {
        holder.price.setText("￥" + mDatas.get(position).getPrice());
        holder.check.setVisibility(checked.contains(Integer.valueOf(position)) ? View.VISIBLE : View.GONE);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checked.contains(Integer.valueOf(position))) {
                    checked.remove(Integer.valueOf(position));
                } else {
                    checked.add(Integer.valueOf(position));
                }
                notifyItemChanged(position);
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public FragmentYhqAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_yhq_item, parent, false);
        return new FragmentYhqAdapter.MyViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView price;
        ImageView check;
        CardView rootView;

        MyViewHolder(View view) {
            super(view);
            rootView = (CardView) view;
            price = view.findViewById(R.id.fragment_yhq_item_price);
            check = view.findViewById(R.id.fragment_yhq_check);
        }
    }
}
