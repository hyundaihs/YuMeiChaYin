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
import com.sp.shangpin.utils.ToastUtil;

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
    private boolean isCheck;
    private int maxNum;
    private int sta;

    public FragmentYhqAdapter(Context context, List<YHQ> datas, boolean check, int max, int status) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        checked = new ArrayList<>();
        isCheck = check;
        maxNum = max;
        sta = status;
    }

    public void setOnItemClickListener(FragmentHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void cleanChecked() {
        checked.clear();
    }

    public ArrayList<YHQ> getCheckedIds() {
        ArrayList<YHQ> ids = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            ids.add(mDatas.get(checked.get(i)));
        }
        return ids;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(FragmentYhqAdapter.MyViewHolder holder, final int position) {
        holder.price.setText(mDatas.get(position).getPrice() + "");
        holder.name.setText(mDatas.get(position).getTitle());
        holder.bg.setBackgroundResource(sta == 2 ? R.mipmap.yhq_pic_used : R.mipmap.yhq_pic_unused);
        if (isCheck) {
            holder.check.setVisibility(checked.contains(Integer.valueOf(position)) ? View.VISIBLE : View.GONE);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checked.contains(Integer.valueOf(position))) {
                        checked.remove(Integer.valueOf(position));
                    } else {
                        if (checked.size() < maxNum) {
                            checked.add(Integer.valueOf(position));
                        } else {
                            ToastUtil.show(mContext, "最多能选" + maxNum + "张");
                        }
                    }
                    notifyItemChanged(position);
                    if (null != onItemClickListener) {
                        onItemClickListener.onItemClick(view, position);
                    }
                }
            });
        }
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
        TextView name;
        View bg;

        MyViewHolder(View view) {
            super(view);
            rootView = (CardView) view;
            name = view.findViewById(R.id.fragment_yhq_item_1);
            price = view.findViewById(R.id.fragment_yhq_item_price);
            check = view.findViewById(R.id.fragment_yhq_check);
            bg = view.findViewById(R.id.fragment_yhq_item_price_layout);
        }
    }
}
