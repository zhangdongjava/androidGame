package com.sina.weibo.sdk;

import android.app.Activity;
import android.widget.Toast;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.demo.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by dell_2 on 2016/12/20.
 */
public class WeiBoResponse extends Activity implements IWeiboHandler.Response, IUiListener {

    private Activity context;

    public WeiBoResponse(Activity context) {

        this.context = context;
    }

    @Override
    public void onResponse(BaseResponse baseResp) {//接收微客户端博请求的数据。
        System.out.println(baseResp.errMsg + "->" + baseResp.reqPackageName);
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(context, "WeiBoResponse extends Activity 分享成功!", Toast.LENGTH_SHORT).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(context, "ERR_CANCEL", Toast.LENGTH_SHORT).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(context, "ERR_FAIL", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCancel() {
        Util.toastMessage(context, "onCancel:test ");
        gotoApp();
    }

    @Override
    public void onError(UiError e) {
        // TODO Auto-generated method stub
        Util.toastMessage(context, "onError: " + e.errorMessage, "e");
        gotoApp();
    }

    @Override
    public void onComplete(Object response) {
        // TODO Auto-generated method stub
        Util.toastMessage(context, "onComplete: " + response.toString());
        gotoApp();
    }

    public void gotoApp() {
        //Intent intent = new Intent(context, AppbarActivity.class);
        //startActivity(intent);
    }
}
