/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sina.weibo.sdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.sina.weibo.sdk.WeiBoResponse;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

/**
 * 该类是整个 DEMO 程序的入口。
 *
 * @author SINA
 * @since 2013-09-29
 */
public class WBDemoMainActivity extends Activity {

    private WeiBoShare weiBoShare;

    private WeiBoResponse shareResponse;

    /**
     * @see {@link Activity#onCreate}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);
        weiBoShare = WeiBoShare.getWeiBoShare();
        weiBoShare.init(this);
        shareResponse = new WeiBoResponse(this);
        Toast.makeText(this,"分享界面创建!",Toast.LENGTH_SHORT).show();
    }


    public void share(View view) {
        // 将应用注册到微博客户端
        sendMultiMessageToWeibo();

    }

    /**
     * 发送多种消息到微博
     */
    private void sendMultiMessageToWeibo() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        // weiboMessage.textObject = getTextObj();
        weiboMessage.mediaObject = getImageObj();
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        weiBoShare.sendRequest(this, request);
        System.out.println(WeiBoShare.getWeiBoShare().getmWeiboShareAPI());

    }

    /**
     * 用异步方式启动分享
     *
     * @param params
     */
    private void doShareToQzone(final Bundle params) {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (null != weiBoShare.mTencent) {
                    weiBoShare.mTencent.shareToQzone(WBDemoMainActivity.this, params, shareResponse);
                }
            }
        });
    }

    /**
     * 用异步方式启动分享
     *
     * @param params
     */
    private void doShareToQQ(final Bundle params) {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (null != weiBoShare.mTencent) {
                    weiBoShare.mTencent.shareToQQ(WBDemoMainActivity.this, params, shareResponse);
                }
            }
        });
    }

    /**
     * 获取文本信息对象
     *
     * @return TextObject
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "分享自...";//这里输入文本
        return textObject;
    }

    /**
     * 获取图片信息对象
     *
     * @return ImageObject
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), com.sina.weibo.sdk.demo.R.drawable.share_img);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    protected void onNewIntent(Intent intent) {
        System.out.println("onNewIntent");
        super.onNewIntent(intent);
        weiBoShare.getmWeiboShareAPI().handleWeiboResponse(intent, new WeiBoResponse(this)); //当前应用唤起微博分享后，返回当前应用
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_QZONE_SHARE
                ||requestCode == com.tencent.connect.common.Constants.REQUEST_QQ_SHARE) {
            Toast.makeText(this, "注册QQ空间分享回调!"+(data==null), Toast.LENGTH_SHORT).show();
            Tencent.onActivityResultData(requestCode, resultCode, data, shareResponse);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void qqShare(View view) {

        doShareToQQ(getQQBundle());
    }

    public Bundle getQQBundle() {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "这是标题");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://douban.fm/?start=8508g3c27g-3&cid=-3");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "SHARE_TO_QQ_SUMMARY");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://e.hiphotos.baidu.com/image/pic/item/83025aafa40f4bfb27bfbf2b014f78f0f7361865.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0x00);
        return params;


    }

    public void qqZoneShare(View view) {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用");
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN); //打开这句话，可以实现分享纯图到QQ空间
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "SHARE_TO_QQ_TITLE");
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "SHARE_TO_QQ_SUMMARY");
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com");
        ArrayList<String> imageUrls = new ArrayList<>();
        // 支持传多个imageUrl
        imageUrls.add("http://e.hiphotos.baidu.com/image/pic/item/83025aafa40f4bfb27bfbf2b014f78f0f7361865.jpg");
        imageUrls.add("http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c672bcdb6d52a6059252da6b7.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, "PUBLISH_TO_QZONE_VIDEO_PATH");
        doShareToQzone(params);

    }
}
