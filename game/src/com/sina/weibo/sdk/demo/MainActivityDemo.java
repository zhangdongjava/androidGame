package com.sina.weibo.sdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.sina.weibo.sdk.WeiBoResponse;

/**
 * Created by dell_2 on 2016/12/19.
 */
public class MainActivityDemo extends Activity {

    private static MainActivityDemo activityDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        activityDemo = this;
    }

    public void share(View view) {

        Aaaaaaaa();
    }

    public static void Aaaaaaaa() {
        Intent intent = new Intent(activityDemo, WBDemoMainActivity.class);
        activityDemo.startActivity(intent);
    }


    protected void onNewIntent(Intent intent) {
        Toast.makeText(this, "MainActivityDemo onNewIntent", Toast.LENGTH_SHORT).show();
        System.out.println(WeiBoShare.getWeiBoShare().getmWeiboShareAPI());
        WeiBoShare.getWeiBoShare().getmWeiboShareAPI().handleWeiboResponse(intent, new WeiBoResponse(this)); //当前应用唤起微博分享后，返回当前应用
    }


}
