package com.sp.shangpin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.adapters.FragmentLottoAdapter;
import com.sp.shangpin.adapters.SpacesItemDecoration;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LottoInfo;
import com.sp.shangpin.entity.LottoInfo_Sup;
import com.sp.shangpin.entity.UpgradeGoods;
import com.sp.shangpin.ui.GoodsDetailsActivity;
import com.sp.shangpin.ui.RuleActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.DisplayUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/4.
 * 抽奖
 */

public class FragmentLotto extends BaseFragment {
    private static BaseFragment baseFragment;
    private final String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RadioGroup radioGroup;
    private int currIndex = 1;
    private ImageView typeImage;
    private LottoInfo lottoInfo;
    private FragmentLottoAdapter adapter;
    private List<UpgradeGoods> data;

    public static BaseFragment getInstance() {
        if (null == baseFragment) {
            baseFragment = new FragmentLotto();
        }
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lotto, container, false);
        toolbar = view.findViewById(R.id.fragment_lotto_toolbar);
        radioGroup = view.findViewById(R.id.fragment_lotto_model_radioGroup);
        typeImage = view.findViewById(R.id.fragment_lotto_type_image);
        recyclerView = view.findViewById(R.id.fragment_lotto_recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
        initViews();
        initListView();
        if (MyApplication.lottoCurrIndex > 0 && MyApplication.lottoCurrIndex < 4) {
            radioGroup.check(MyApplication.lottoCurrIndex == 1 ? R.id.fragment_lotto_model_flag1 :
                    MyApplication.lottoCurrIndex == 2 ? R.id.fragment_lotto_model_flag2 : R.id.fragment_lotto_model_flag3);
            currIndex = MyApplication.lottoCurrIndex;
            MyApplication.lottoCurrIndex = -1;
        }
        getDataInfo();
    }

    private void getDataInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("type_id", String.valueOf(currIndex));
        VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.goodssj(),
                JsonUtil.objectToString(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InterResult interResult =
                                (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                        if (interResult.isSuccessed()) {
                            LottoInfo_Sup lottoInfo_sup = (LottoInfo_Sup) JsonUtil.stringToObject(response.toString(), LottoInfo_Sup.class);
                            Log.i(TAG, "抽奖信息获取成功");
                            lottoInfo = lottoInfo_sup.getRetRes();
                            refresh();
                        } else {
                            DialogUtil.showErrorMessage(getActivity(), interResult.getRetErr());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogUtil.showErrorMessage(getActivity(), error.toString());
                    }
                });
        volleyUtil.addToRequestQueue(request, InternetUtil.reg());
    }

    private void initViews() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.fragment_lotto_model_flag1:
                        currIndex = 1;
                        break;
                    case R.id.fragment_lotto_model_flag2:
                        currIndex = 2;
                        break;
                    case R.id.fragment_lotto_model_flag3:
                        currIndex = 3;
                        break;
                    default:
                        break;
                }
                getDataInfo();
            }
        });
    }

    private void refresh() {
        if (null != lottoInfo) {
            if (null != lottoInfo.getInfo()) {
                VolleyUtil volleyUtil = VolleyUtil.getInstance(getActivity());
                volleyUtil.getImage(typeImage, lottoInfo.getInfo().getBanner_file_url());
            }
            if (null != lottoInfo.getLists()) {
                data.clear();
                data.addAll(lottoInfo.getLists());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initListView() {
        data = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new FragmentLottoAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtil.dp2px(getActivity(), 10)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new FragmentHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                intent.putExtra("id", data.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void initActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_lotto);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_lotto, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lotto_rule:
                startActivity(new Intent(getActivity(), RuleActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
