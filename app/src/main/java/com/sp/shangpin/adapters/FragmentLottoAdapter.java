package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp.shangpin.R;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.utils.VolleyUtil;

import java.util.List;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/6.
 */

public class FragmentLottoAdapter extends RecyclerView.Adapter<FragmentLottoAdapter.MyViewHolder> {
    private List<UpgradeGoods> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;

    public FragmentLottoAdapter(Context context, List<UpgradeGoods> datas) {
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
    public void onBindViewHolder(FragmentLottoAdapter.MyViewHolder holder, final int position) {
        UpgradeGoods upgradeGoods = mDatas.get(position);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(mContext);
        volleyUtil.getImage(holder.notUpgradeImage, upgradeGoods.getFile_url());
        volleyUtil.getImage(holder.upgradeImage, upgradeGoods.getFile_url_2());
        holder.notUpgradeName.setText(mDatas.get(position).getTitle());
        holder.upgradeName.setText(mDatas.get(position).getTitle_2());
        holder.notUpgradePrice.setText("¥" + mDatas.get(position).getPrice());
        holder.upgradePrice.setText("¥" + mDatas.get(position).getPrice_2());
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
    public FragmentLottoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_lotto_list_item, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView notUpgradeImage, upgradeImage;
        TextView notUpgradeName, upgradeName, notUpgradePrice, upgradePrice;
        View rootView;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            notUpgradeImage = view.findViewById(R.id.fragment_lotto_list_item_not_upgrade_image);
            upgradeImage = view.findViewById(R.id.fragment_lotto_list_item_upgrade_image);
            notUpgradeName = view.findViewById(R.id.fragment_lotto_list_item_not_upgrade_name);
            upgradeName = view.findViewById(R.id.fragment_lotto_list_item_upgrade_name);
            notUpgradePrice = view.findViewById(R.id.fragment_lotto_list_item_not_upgrade_price);
            upgradePrice = view.findViewById(R.id.fragment_lotto_list_item_upgrade_price);
        }

    }
}