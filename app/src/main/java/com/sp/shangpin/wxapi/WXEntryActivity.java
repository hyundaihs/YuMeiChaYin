package com.sp.shangpin.wxapi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;
import com.sp.shangpin.entity.AccessToken;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.WXUserInfo;
import com.sp.shangpin.ui.BindPhoneActivity;
import com.sp.shangpin.ui.HomeActivity;
import com.sp.shangpin.utils.InternetUtil;
import com.sp.shangpin.utils.JsonUtil;
import com.sp.shangpin.utils.LoginUtil;
import com.sp.shangpin.utils.SharedPreferencesUtil;
import com.sp.shangpin.utils.ToastUtil;
import com.sp.shangpin.utils.VolleyUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private final String TAG = getClass().getSimpleName();
    private final Context thisContext = this;

    private TextView wx_result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        initActionBar();
        wx_result = (TextView) findViewById(R.id.wx_result);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            MyApplication.api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("登录");
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        MyApplication.api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        Log.d("onReq", "回调起来了 req = " + req.toString());
    }

    @Override
    public void onResp(BaseResp resp) {
        int errorCode = resp.errCode;
        Log.d("onResp", "回调起来了 code = " + errorCode);
        switch (errorCode) {
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                String code = ((SendAuth.Resp) resp).code;
                String openId = (String) SharedPreferencesUtil.getParam(thisContext, SharedKey.ACCESS_TOKEN, "");
                wx_result.setText("载入中...");
                Log.d(TAG, openId);
                if (openId.equals("")) {
                    access_token(true, code);
                } else {
                    check_token(code);
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝
                wx_result.setText("用户拒绝");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                wx_result.setText("用户取消");
                break;
            default:
                ToastUtil.show(this, resp.errStr + "errorCode = " + errorCode);
                break;
        }
    }

    private void check_token(final String code) {
        wx_result.setText("检查Token");
        AccessToken accessToken = getTokenInfo();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        StringRequest request = new StringRequest(Request.Method.GET, InternetUtil.check_token(accessToken.getAccess_token(), accessToken.getOpenid()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AccessToken accessToken = (AccessToken) JsonUtil.stringToObject(response, AccessToken.class);
                        if (accessToken.getErrcode() == 0) {
                            Log.d(TAG, accessToken.toString());
                            wx_result.setText("Token 有效");
                            refresh_token();
                        } else {
                            Log.e(TAG, accessToken.getErrmsg());
                            wx_result.setText("Token 无效");
                            access_token(false, code);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wx_result.setText(error.toString());
            }
        });
        volleyUtil.addToRequestQueue(request, InternetUtil.login());
    }

    private void saveTokenInfo(AccessToken accessToken) {
        SharedPreferencesUtil.setParam(thisContext, SharedKey.ACCESS_TOKEN, accessToken.getAccess_token());
        SharedPreferencesUtil.setParam(thisContext, SharedKey.REFRESH_TOKEN, accessToken.getRefresh_token());
//        SharedPreferencesUtil.setParam(thisContext, SharedKey.OPENID, accessToken.getOpenid());
        SharedPreferencesUtil.setParam(thisContext, SharedKey.SCOPE, accessToken.getScope());
        SharedPreferencesUtil.setParam(thisContext, SharedKey.UNIONID, accessToken.getUnionid());
    }

    public AccessToken getTokenInfo() {
        AccessToken accessToken = new AccessToken();
        accessToken.setAccess_token((String) SharedPreferencesUtil.getParam(thisContext, SharedKey.ACCESS_TOKEN, ""));
        accessToken.setRefresh_token((String) SharedPreferencesUtil.getParam(thisContext, SharedKey.REFRESH_TOKEN, ""));
//        accessToken.setOpenid((String) SharedPreferencesUtil.getParam(thisContext, SharedKey.OPENID, ""));
        accessToken.setScope((String) SharedPreferencesUtil.getParam(thisContext, SharedKey.SCOPE, ""));
        accessToken.setUnionid((String) SharedPreferencesUtil.getParam(thisContext, SharedKey.UNIONID, ""));
        return accessToken;
    }

    public void access_token(final boolean isFirst, String code) {
        wx_result.setText("获取Token");
        final String appid = InternetUtil.getWChatAppId();
        String secret = InternetUtil.getWChatSecret();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        StringRequest request = new StringRequest(Request.Method.GET, InternetUtil.access_token(appid, secret, code),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AccessToken accessToken = (AccessToken) JsonUtil.stringToObject(response, AccessToken.class);
                        if (accessToken.getErrcode() == 0) {
                            Log.d(TAG, accessToken.toString());
                            wx_result.setText("Token 获取成功");
                            //saveTokenInfo(accessToken);
                            if (isFirst) {
                                getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
                            } else {
                                login(accessToken.getOpenid());
                            }
                        } else {
                            wx_result.setText("Token 获取失败");
                            Log.e(TAG, accessToken.getErrmsg());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wx_result.setText(error.toString());
            }
        });
        volleyUtil.addToRequestQueue(request, InternetUtil.login());
    }

    private void refresh_token() {
        wx_result.setText("刷新Token");
        String appid = InternetUtil.getWChatAppId();
        String refresh_token = getTokenInfo().getRefresh_token();
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        StringRequest request = new StringRequest(Request.Method.GET, InternetUtil.refresh_token(appid, refresh_token),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AccessToken accessToken = (AccessToken) JsonUtil.stringToObject(response, AccessToken.class);
                        if (accessToken.getErrcode() == 0) {
                            Log.d(TAG, accessToken.toString());
                            wx_result.setText("Token 刷新成功");
                            saveTokenInfo(accessToken);
                            login(accessToken.getOpenid());
                        } else {
                            wx_result.setText("Token 刷新失败");
                            Log.e(TAG, accessToken.getErrmsg());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wx_result.setText(error.toString());
            }
        });
        volleyUtil.addToRequestQueue(request, InternetUtil.login());
    }

    private void getUserInfo(String accessToken, String openid) {
        wx_result.setText("获取用户信息");
        VolleyUtil volleyUtil = VolleyUtil.getInstance(thisContext);
        StringRequest request = new StringRequest(Request.Method.GET, InternetUtil.wx_userinfo(accessToken, openid),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        WXUserInfo wxUserInfo = (WXUserInfo) JsonUtil.stringToObject(response, WXUserInfo.class);
                        if (wxUserInfo.getErrcode() == 0) {
                            wx_result.setText("用户信息获取成功");
                            Log.d(TAG, wxUserInfo.toString());
                            SharedPreferencesUtil.setParam(thisContext, SharedKey.UNIONID, wxUserInfo.getUnionid());
                            Intent intent = new Intent(thisContext, BindPhoneActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("user_info", wxUserInfo);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e(TAG, wxUserInfo.getErrmsg());
                            wx_result.setText("用户信息获取失败");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wx_result.setText(error.toString());
            }
        });
        volleyUtil.addToRequestQueue(request, InternetUtil.login());
    }

    private void login(final String openid) {
        wx_result.setText("正在登录...");
        LoginUtil.verfLogin(thisContext, true, openid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                InterResult interResult = (InterResult) JsonUtil.stringToObject(response.toString(), InterResult.class);
                if (interResult.isSuccessed()) {
                    wx_result.setText("登录成功");
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, true);
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.ISWX_LOGIN, true);
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.OPENID, openid);
                    startActivity(new Intent(thisContext, HomeActivity.class));
                    MyApplication.cleanAllActivitys();
                } else {
                    wx_result.setText("登录失败");
                    SharedPreferencesUtil.setParam(thisContext, SharedKey.IS_REMEMBER, false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wx_result.setText(error.toString());
                Log.e(TAG, error.toString());
            }
        });
    }

}