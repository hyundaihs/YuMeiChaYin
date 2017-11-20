package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.NormalGoodsInfo;
import com.sp.shangpin.entity.NormalOrderType;
import com.sp.shangpin.utils.VolleyUtil;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsAdapter extends RecyclerView.Adapter<NormalGoodsAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<NormalGoodsInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;
    private int orderType;

    public NormalGoodsAdapter(Context context, List<NormalGoodsInfo> datas, int orderType) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        this.orderType = orderType;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        VolleyUtil volleyUtil = VolleyUtil.getInstance(mContext);
        volleyUtil.getImageByNetwork(holder.image, mDatas.get(position).getFile_url());
        holder.name.setText(mDatas.get(position).getTitle());

        switch (orderType) {
            case NormalOrderType.ORIGINAL:
                holder.price.setText("商城价:¥" + mDatas.get(position).getPrice());
                holder.price.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.oPriceTitle.setVisibility(View.GONE);
                holder.oPrice.setVisibility(View.GONE);
                holder.priceTitle.setVisibility(View.GONE);
                break;
            case NormalOrderType.GOLD:
                holder.price.setText(mDatas.get(position).getPrice() + "金币");
                holder.price.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.oPriceTitle.setVisibility(View.GONE);
                holder.oPrice.setVisibility(View.GONE);
                holder.priceTitle.setVisibility(View.GONE);
                break;
            case NormalOrderType.ON_SALE:
                holder.oPrice.setText(mDatas.get(position).getYuanjia() + "");
                holder.price.setText(mDatas.get(position).getPrice() + "");
                holder.price.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
                holder.oPriceTitle.setVisibility(View.VISIBLE);
                holder.oPrice.setVisibility(View.VISIBLE);
                holder.priceTitle.setVisibility(View.VISIBLE);
                break;
        }


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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_home_list_item, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView image;
        TextView name, oPrice, oPriceTitle, price, priceTitle;
        View rootView;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            image = view.findViewById(R.id.fragment_home_list_item_image);
            name = view.findViewById(R.id.fragment_home_list_item_name);
            oPrice = view.findViewById(R.id.fragment_home_list_item_oPrice);
            price = view.findViewById(R.id.fragment_home_list_item_price);
            oPriceTitle = view.findViewById(R.id.fragment_home_list_item_oPrice_title);
            priceTitle = view.findViewById(R.id.fragment_home_list_item_price_title);
        }
    }
}
