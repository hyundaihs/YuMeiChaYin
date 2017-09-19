package com.sp.shangpin.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.NormalOrderInfo;
import com.sp.shangpin.entity.OrderStatus;
import com.sp.shangpin.entity.RequestAndResult;
import com.sp.shangpin.fragments.BaseFragment;
import com.sp.shangpin.ui.InputAddrActivity;
import com.sp.shangpin.ui.LotteryActivity;
import com.sp.shangpin.utils.CalendarUtil;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalOrdersAdapter extends RecyclerView.Adapter<NormalOrdersAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<NormalOrderInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;
    private int index;
    private BaseFragment fragment;

    public NormalOrdersAdapter(Context context, BaseFragment fragment, List<NormalOrderInfo> datas, int index) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        this.index = index;
        this.fragment = fragment;
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
    public void onBindViewHolder(NormalOrdersAdapter.MyViewHolder holder, final int position) {
        NormalOrderInfo normalOrderInfo = mDatas.get(position);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(mContext);
        holder.orderTime.setText("下单时间:" + new CalendarUtil(normalOrderInfo.getCreate_time(), true)
                .format(CalendarUtil.STANDARD));
        volleyUtil.getImage(holder.image, normalOrderInfo.getGoods_lists().get(0).getGoods_file_url());
        holder.name.setText(normalOrderInfo.getGoods_lists().get(0).getGoods_title());
        holder.number.setText("x" + normalOrderInfo.getGoods_lists().get(0).getNum());
        holder.status.setText(OrderStatus.STRINGS[normalOrderInfo.getStatus()]);
        holder.price.setText("￥" + normalOrderInfo.getGoods_lists().get(0).getPrice());
        holder.totalPrice.setText("￥" + normalOrderInfo.getPrice());
        holder.returnG.setOnClickListener(new NormalOrdersAdapter.MyOnClickListener(position));
        holder.details.setOnClickListener(new NormalOrdersAdapter.MyOnClickListener(position));
        holder.goodsFlag.setVisibility(normalOrderInfo.getCx() == 1 ? View.VISIBLE : View.GONE);
        holder.addrName.setText("收货人:" + normalOrderInfo.getTitle());
        holder.addrPhone.setText("手机:" + normalOrderInfo.getPhone());
        holder.addrArea.setText("收货地址" + normalOrderInfo.getPca());
        holder.addrAddr.setText("详细地址" + normalOrderInfo.getAddress());
        holder.addrContent.setText("备注:" + normalOrderInfo.getContents());
        switch (index) {
            case 0:
                holder.returnG.setVisibility(View.VISIBLE);
                holder.returnG.setText("退款");
                break;
            case 1:
                holder.returnG.setVisibility(View.GONE);
                break;
            case 2:
                holder.returnG.setVisibility(View.VISIBLE);
                holder.returnG.setText("确认收货");
                break;
            default:
                holder.returnG.setVisibility(View.GONE);
                break;
        }

        if (null != onItemClickListener) {
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    private void returnGoods(final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("orders_id", String.valueOf(mDatas.get(position).getId()));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(mContext);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.unsetorders(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            mDatas.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            DialogUtil.showErrorMessage(mContext, interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(mContext, error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public NormalOrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.normal_orders_list_item, parent, false);
        return new NormalOrdersAdapter.MyViewHolder(view);
    }

    private class MyOnClickListener implements View.OnClickListener {
        int position;

        MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.normal_orders_list_item_return:
                    if (index == 2) {//确认收货

                    } else {
                        DialogUtil.showAskMessage(mContext, "确定要退款吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                returnGoods(position);
                            }
                        });
                    }
                    break;
                case R.id.normal_orders_list_item_details:
                    break;
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        ImageView image, goodsFlag;
        TextView orderTime, orderId, name, number, status, returnG, price, totalPrice, details,
                addrName, addrPhone, addrArea, addrAddr, addrContent;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            orderTime = view.findViewById(R.id.normal_orders_list_item_order_time);
            orderId = view.findViewById(R.id.normal_orders_list_item_order_id);
            image = view.findViewById(R.id.normal_orders_list_item_image);
            name = view.findViewById(R.id.normal_orders_list_item_name);
            number = view.findViewById(R.id.normal_orders_list_item_number);
            status = view.findViewById(R.id.normal_orders_list_item_status);
            goodsFlag = view.findViewById(R.id.normal_orders_list_item_goods_flag);
            returnG = view.findViewById(R.id.normal_orders_list_item_return);
            price = view.findViewById(R.id.normal_orders_list_item_price);
            totalPrice = view.findViewById(R.id.normal_orders_list_item_total_price);
            details = view.findViewById(R.id.normal_orders_list_item_details);
            addrName = view.findViewById(R.id.normal_orders_list_item_details);
            addrPhone = view.findViewById(R.id.normal_orders_list_item_details);
            addrArea = view.findViewById(R.id.normal_orders_list_item_details);
            addrAddr = view.findViewById(R.id.normal_orders_list_item_details);
            addrContent = view.findViewById(R.id.normal_orders_list_item_details);
        }
    }
}