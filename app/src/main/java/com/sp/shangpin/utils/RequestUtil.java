package com.sp.shangpin.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class RequestUtil {

    public static JsonObjectRequest createJsonGetRequest(String action, JSONObject jsonObject,
                                                         Response.Listener<JSONObject> listener,
                                                         final MyErrorListener myErrorListener) {
        return new JsonObjectRequest(Request.Method.GET,
                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
                jsonObject, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != myErrorListener) {
                    myErrorListener.onErrorResponse(getErrorString(error));
                }
            }
        });
    }

    public static StringRequest createStringGetRequest(String action, Response.Listener<String> listener,
                                                       final MyErrorListener myErrorListener) {
        return new StringRequest(Request.Method.GET,
                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
                listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != myErrorListener) {
                    myErrorListener.onErrorResponse(getErrorString(error));
                }
            }
        });

    }

//    public static JsonObjectRequest createPostJsonRequest(String action, String jsonObject,
//                                                          Response.Listener<JSONObject> listener,
//                                                          MyErrorListener myErrorListener) {
//        return new JsonObjectRequest(Request.Method.POST,
//                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
//                jsonObject, listener, errorListener);
//    }

    public static JsonObjectPostRequest createPostJsonRequest(String action, String requestBody,
                                                              Response.Listener<JSONObject> listener,
                                                              final MyErrorListener myErrorListener) {
        JsonObjectPostRequest jsonObjectRequest = new JsonObjectPostRequest(
                InternetUtil.urlApi() + action + InternetUtil.securityStr(),
                requestBody, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myErrorListener.onErrorResponse(getErrorString(error));
            }
        });
        jsonObjectRequest.setSendCookie();//向服务器发起post请求时加上cookie字段
        return jsonObjectRequest;
    }

    public interface MyErrorListener {
        void onErrorResponse(String error);
    }

    private static String getErrorString(VolleyError error){
        /**
         * AuthFailureError：如果在做一个HTTP的身份验证，可能会发生这个错误。
         NetworkError：Socket关闭，服务器宕机，DNS错误都会产生这个错误。
         NoConnectionError：和NetworkError类似，这个是客户端没有网络连接。
         ParseError：在使用JsonObjectRequest或JsonArrayRequest时，如果接收到的JSON是畸形，会产生异常。
         SERVERERROR：服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码。
         TimeoutError：
         */
        String err = error.toString();
        if(err.contains("AuthFailureError")){
            err = "HTTP的身份验证错误,请检查网络后再试";
        }else if(err.contains("NetworkError")){
            err = "服务器没有响应,请检查网络后再试";
        }else if(err.contains("NoConnectionError")){
            err = "网络链接错误,请检查网络后再试";
        }else if(err.contains("ParseError")){
            err = "获取的服务器数据有误,请稍后再试";
        }else if(err.contains("SERVERERROR")){
            err = "服务器响应错误,请稍后再试";
        }else if(err.contains("TimeoutError")){
            err = "链接超时,请检查网络后再试";
        }else{
            err = "其他未知错误,请重启应用后再试";
        }
        return err;
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
