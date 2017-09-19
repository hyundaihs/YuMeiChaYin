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
import com.sp.shangpin.entity.OrderInfo;
import com.sp.shangpin.entity.OrderStatus;
import com.sp.shangpin.entity.RequestAndResult;
import com.sp.shangpin.entity.UpgradeStatus;
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
 * Created by ${蔡雨峰} on 2017/9/17/017.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<OrderInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentHomeAdapter.OnItemClickListener onItemClickListener;
    private int index;
    private BaseFragment fragment;

    public OrdersAdapter(Context context, BaseFragment fragment, List<OrderInfo> datas, int index) {
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
    public void onBindViewHolder(OrdersAdapter.MyViewHolder holder, final int position) {
        OrderInfo orderInfo = mDatas.get(position);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(mContext);
        if (orderInfo.getSj_status() == UpgradeStatus.UPGRADE_SUCCESS) {
            holder.orderTime.setText("升级时间:" + new CalendarUtil(orderInfo.getSj_time(), true).format(CalendarUtil.STANDARD));
            volleyUtil.getImage(holder.image, orderInfo.getGoodssj_file_url_2());
            holder.orderId.setText("订单:" + orderInfo.getNumbers());
            holder.name.setText(orderInfo.getGoodssj_title_2());
            holder.number.setText("x" + orderInfo.getNum());
            holder.status.setText(OrderStatus.STRINGS[orderInfo.getStatus()] + "(" + UpgradeStatus.STRINGS[orderInfo.getSj_status()] + ")");
            holder.price.setText("￥" + orderInfo.getGoodssj_price_2());
            holder.totalPrice.setText("￥" + orderInfo.getPrice_2());
        } else {
            holder.orderTime.setText("下单时间:" + new CalendarUtil(orderInfo.getCreate_time(), true).format(CalendarUtil.STANDARD));
            volleyUtil.getImage(holder.image, orderInfo.getGoodssj_file_url());
            holder.orderId.setText("订单:" + orderInfo.getNumbers());
            holder.name.setText(orderInfo.getGoodssj_title());
            holder.number.setText("x" + orderInfo.getNum());
            holder.status.setText(OrderStatus.STRINGS[orderInfo.getStatus()] + "(" + UpgradeStatus.STRINGS[orderInfo.getSj_status()] + ")");
            holder.price.setText("￥" + orderInfo.getDanjia());
            holder.totalPrice.setText("￥" + orderInfo.getPrice());
        }
        holder.goldCoin.setOnClickListener(new MyOnClickListener(position));
        holder.returnG.setOnClickListener(new MyOnClickListener(position));
        holder.pickUp.setOnClickListener(new MyOnClickListener(position));
        holder.upgrade.setOnClickListener(new MyOnClickListener(position));
        holder.details.setOnClickListener(new MyOnClickListener(position));
        switch (index) {
            case 0:
                holder.goldCoin.setVisibility(View.GONE);
                holder.lotteryTime.setText("");
                holder.returnG.setVisibility(View.VISIBLE);
                holder.pickUp.setVisibility(View.VISIBLE);
                holder.upgrade.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.goldCoin.setVisibility(View.VISIBLE);
                holder.lotteryTime.setText("开奖时间:" + orderInfo.getKj_dates());
                holder.returnG.setVisibility(View.GONE);
                holder.pickUp.setVisibility(View.VISIBLE);
                holder.upgrade.setVisibility(View.GONE);
                break;
            case 2:
                holder.goldCoin.setVisibility(View.GONE);
                holder.returnG.setVisibility(View.GONE);
                holder.lotteryTime.setText("");
                holder.pickUp.setVisibility(View.GONE);
                holder.upgrade.setVisibility(View.GONE);
                break;
            case 3:
                holder.goldCoin.setVisibility(View.GONE);
                holder.returnG.setVisibility(View.GONE);
                holder.lotteryTime.setText("");
                holder.pickUp.setVisibility(View.GONE);
                holder.upgrade.setVisibility(View.GONE);
                break;
            default:
                holder.goldCoin.setVisibility(View.GONE);
                holder.returnG.setVisibility(View.GONE);
                holder.lotteryTime.setText("");
                holder.pickUp.setVisibility(View.GONE);
                holder.upgrade.setVisibility(View.GONE);
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
        map.put("orderssj_id", String.valueOf(mDatas.get(position).getId()));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(mContext);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.sjth(),
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
    public OrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.orders_list_item, parent, false);
        return new OrdersAdapter.MyViewHolder(view);
    }


    private void goInputAddr(int position) {
        Intent intent = new Intent(mContext, InputAddrActivity.class);
        intent.putExtra("position", position);
        fragment.startActivityForResult(intent, RequestAndResult.REQUEST_FROM_PAID);
    }

    private void goLottery(int position) {
        Intent intent = new Intent(mContext, LotteryActivity.class);
        intent.putExtra("order_id", mDatas.get(position).getId());
        fragment.startActivity(intent);
    }

    private class MyOnClickListener implements View.OnClickListener {
        int position;

        MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.orders_list_item_gold_coin:
                    break;
                case R.id.orders_list_item_return:
                    DialogUtil.showAskMessage(mContext, "确定要退款吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            returnGoods(position);
                        }
                    });
                    break;
                case R.id.orders_list_item_pick_up:
                    goInputAddr(position);
                    break;
                case R.id.orders_list_item_upgrade:
                    goLottery(position);
                    break;
                case R.id.orders_list_item_details:
                    break;
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        ImageView image;
        TextView orderTime, orderId, name, number, status, lotteryTime, goldCoin, returnG, pickUp, upgrade, price, totalPrice, details;

        MyViewHolder(View view) {
            super(view);
            rootView = view;
            orderTime = view.findViewById(R.id.orders_list_item_order_time);
            orderId = view.findViewById(R.id.orders_list_item_order_id);
            image = view.findViewById(R.id.orders_list_item_image);
            name = view.findViewById(R.id.orders_list_item_name);
            number = view.findViewById(R.id.orders_list_item_number);
            status = view.findViewById(R.id.orders_list_item_status);
            lotteryTime = view.findViewById(R.id.orders_list_item_lottery_time);
            goldCoin = view.findViewById(R.id.orders_list_item_gold_coin);
            returnG = view.findViewById(R.id.orders_list_item_return);
            pickUp = view.findViewById(R.id.orders_list_item_pick_up);
            upgrade = view.findViewById(R.id.orders_list_item_upgrade);
            price = view.findViewById(R.id.orders_list_item_price);
            totalPrice = view.findViewById(R.id.orders_list_item_total_price);
            details = view.findViewById(R.id.orders_list_item_details);
        }
    }
}