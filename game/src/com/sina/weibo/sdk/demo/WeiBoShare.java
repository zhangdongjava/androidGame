package com.sina.weibo.sdk.demo;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.tauth.Tencent;

/**
 * Created by dell_2 on 2016/12/20.
 */
public class WeiBoShare {

    public IWeiboShareAPI mWeiboShareAPI;

    public Tencent mTencent;

    private boolean isregis;

    public static WeiBoShare weiBoShare = new WeiBoShare();

    public IWeiboShareAPI getmWeiboShareAPI() {
        return mWeiboShareAPI;
    }

    private WeiBoShare() {
        isregis = false;
    }

    public void init(Context context) {

        if (mTencent == null) {
            mTencent = Tencent.createInstance("222222", context);
        }
        if (mWeiboShareAPI == null) {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Constants.APP_KEY);
        }
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            Toast.makeText(context, "请安装了微博客户端再分享:", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isregis) {
            mWeiboShareAPI.registerApp();
            isregis = true;
        }
    }

    public static WeiBoShare getWeiBoShare() {
        return weiBoShare;
    }

    public void sendRequest(Activity activity, SendMultiMessageToWeiboRequest request) {
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            Toast.makeText(activity, "请安装了微博客户端再分享:", Toast.LENGTH_SHORT).show();
        } else {
            mWeiboShareAPI.sendRequest(activity, request);
        }

    }

}
