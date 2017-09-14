package com.sp.shangpin.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class RequestUtil {

    public static String cookie = "";

    public static JsonObjectRequest createJsonGetRequest(String action, JSONObject jsonObject,
                                                         Response.Listener<JSONObject> listener,
                                                         Response.ErrorListener errorListener) {
        return new JsonObjectRequest(Request.Method.GET,
                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
                jsonObject, listener, errorListener);
    }

    public static StringRequest createStringGetRequest(String action, Response.Listener<String> listener,
                                                       Response.ErrorListener errorListener) {
        return new StringRequest(Request.Method.GET,
                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
                listener, errorListener);

    }

    public static JsonObjectRequest createPostJsonRequest(String action, String jsonObject,
                                                          Response.Listener<JSONObject> listener,
                                                          Response.ErrorListener errorListener) {
        return new JsonObjectRequest(Request.Method.POST,
                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
                jsonObject, listener, errorListener);
    }

    public static JsonObjectPostRequest createJsonObjectPostRequest(String action, String requestBody,
                                                                    Response.Listener<JSONObject> listener,
                                                                    Response.ErrorListener errorListener) {
        JsonObjectPostRequest jsonObjectRequest = new JsonObjectPostRequest(
                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
                requestBody, listener, errorListener);
        if (action.equals(InternetUtil.userinfo())) {
            jsonObjectRequest.setSendCookie(cookie);//向服务器发起post请求时加上cookie字段
        }
        return jsonObjectRequest;
    }


//    /**
//     * Post参数
//     */
//    @Override
//    protected Map<String, String> getParams() {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("name", "Androidhive");
//        params.put("email", "abc@androidhive.info");
//        params.put("password", "password123");
//
//        return params;
//    }
//
//    /**
//     * 添加头信息
//     * */
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/json");
//        headers.put("apiKey", "xxxxxxxxxxxxxxx");
//        return headers;
//    }
}
