package com.sp.shangpin.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sp.shangpin.MainActivity;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.entity.InterResult;
import com.sp.shangpin.entity.LoginInfo_Sup;
import com.sp.shangpin.entity.SharedKey;
import com.sp.shangpin.entity.SystemInfo_Sup;
import com.sp.shangpin.ui.HomeActivity;
import com.sp.shangpin.ui.LoginActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/29/029.
 */

public class LoginUtil {

    public static void login(Context context, final String account, final String password,
                             Response.Listener<JSONObject> listener, RequestUtil.MyErrorListener errorListener) {
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("password", password);
        VolleyUtil volleyUtil = VolleyUtil.getInstance(context);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.login(),
                JsonUtil.objectToString(map), listener, errorListener);
        volleyUtil.addToRequestQueue(request, InternetUtil.login());

    }

    public static void verfLogin(Context context, boolean isWx, final String string
            , Response.Listener<JSONObject> listener, RequestUtil.MyErrorListener errorListener) {
        Map<String, String> map = new HashMap<>();
        if (isWx) {
            map.put("openid", string);
        } else {
            map.put("login_verf", string);
        }
        VolleyUtil volleyUtil = VolleyUtil.getInstance(context);
        JsonObjectRequest request = RequestUtil.createPostJsonRequest(InternetUtil.verflogin(),
                JsonUtil.objectToString(map), listener, errorListener);
        volleyUtil.addToRequestQueue(request, InternetUtil.verflogin());

    }
}
