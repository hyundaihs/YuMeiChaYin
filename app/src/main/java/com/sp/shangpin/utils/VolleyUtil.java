package com.sp.shangpin.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.sp.shangpin.MyApplication;
import com.sp.shangpin.R;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class VolleyUtil {

    private static VolleyUtil volleyUtil;
    private final String TAG = getClass().getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context context;

    private VolleyUtil(Context context) {
        this.context = context;
    }

    public static VolleyUtil getInstance(Context context) {
        if (null == volleyUtil) {
            volleyUtil = new VolleyUtil(context);
        }
        return volleyUtil;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, @Nullable String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

//    public <T> void addToRequestQueue(Request<T> req) {
//        req.setTag(TAG);
//        getRequestQueue().add(req);
//    }

    public void cancelPendingRequests(@Nullable Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(null == tag ? TAG : tag);
        }
    }

    public void getImage(ImageView imageView, String url) {
        ImageLoader imageLoader = getImageLoader();
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(MyApplication.systemInfo.getBase_href() + url, imageListener);
    }

    public void getImageByNetwork(NetworkImageView imageView,String url){
        ImageLoader imageLoader = getImageLoader();
        imageView.setDefaultImageResId(R.mipmap.ic_launcher);
        imageView.setErrorImageResId(R.mipmap.ic_launcher);
        imageView.setImageUrl(MyApplication.systemInfo.getBase_href() + url, imageLoader);
    }

}
