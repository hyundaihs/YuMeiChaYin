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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.adapters.FragmentHomeAdapter;
import com.sp.shangpin.adapters.FragmentLottoAdapter;
import com.sp.shangpin.entity.HomeInfo_Sup;
import com.sp.shangpin.entity.LoginInfo;
import com.sp.shangpin.entity.LottoInfo;
import com.sp.shangpin.entity.LottoInfo_Sup;
import com.sp.shangpin.ui.GoodsDetailsActivity;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.RequestUtil;
import com.sp.shangpin.utils.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2017/9/4.
 */

public class FragmentLotto extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private RecyclerView recyclerView;
    private RadioGroup radioGroup;
    private int currIndex = 1;
    private ImageView typeImage;
    private LottoInfo lottoInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lotto, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
        initViews();
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
                        LottoInfo_Sup lottoInfo_sup = (LottoInfo_Sup) JsonUtil.stringToObject(response.toString(), LottoInfo_Sup.class);
                        if (lottoInfo_sup.isSuccessed()) {
                            lottoInfo = lottoInfo_sup.getRetRes();
                            refresh();
                        } else {
                            DialogUtil.showErrorMessage(getActivity(), lottoInfo_sup.getRetErr());
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
        radioGroup = getView().findViewById(R.id.fragment_lotto_model_radioGroup);
        typeImage = getView().findViewById(R.id.fragment_lotto_type_image);
        recyclerView = getView().findViewById(R.id.fragment_lotto_recyclerView);
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
            initListView();
        }
    }

    private void initListView() {
        if (null != lottoInfo.getLists()) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            FragmentLottoAdapter adapter = new FragmentLottoAdapter(getActivity(), lottoInfo.getLists());
            recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.setOnItemClickListener(new FragmentHomeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                    intent.putExtra("id", lottoInfo.getLists()[position].getId());
                    startActivity(intent);
                }
            });
        }
    }

    public void initActionBar() {
        Toolbar toolbar = getView().findViewById(R.id.fragment_lotto_toolbar);
        toolbar.inflateMenu(R.menu.menu_home);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_rule:
                Toast.makeText(getActivity(), "规则", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
