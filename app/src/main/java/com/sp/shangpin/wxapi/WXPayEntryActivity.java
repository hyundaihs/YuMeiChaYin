package com.sp.shangpin.wxapi;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sp.shangpin.R;
import com.sp.shangpin.utils.DialogUtil;
import com.sp.shangpin.utils.SnackbarUtil;
import com.sp.shangpin.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import static android.content.ContentValues.TAG;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/10/28/028.
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("onReq", "回调");
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("onResp", "回调");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.d(TAG, "onPayFinish,errCode=" + resp.errCode);
            String message;
            switch (resp.errCode) {
                case 0:
                    message = "支付成功";
                    break;
                case -1:
                    message = "支付失败,参数错误";
                    break;
                case -2:
                    message = "取消支付";
                    break;
                default:
                    message = "未知错误";
                    break;
            }
            ToastUtil.show(this,message);
        }
        finish();
    }
}
