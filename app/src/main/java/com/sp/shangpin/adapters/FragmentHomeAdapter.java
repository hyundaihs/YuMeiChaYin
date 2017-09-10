package com.sp.shangpin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.toolbox.NetworkImageView;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.utils.VolleyUtil;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/5.
 */

public class FragmentHomeAdapter extends RecyclerView.Adapter<FragmentHomeAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private UpgradeGoods[] mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public FragmentHomeAdapter(Context context, UpgradeGoods[] datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.length;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        holder.image.setText(mDatas.get(position));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(mContext);
        volleyUtil.getImageByNetwork(holder.image, mDatas[position].getFile_url());
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
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView image;
        View rootView;

        public MyViewHolder(View view) {
            super(view);
            rootView = view;
            image = view.findViewById(R.id.fragment_home_list_item_image);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
