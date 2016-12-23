package com.zzz.game.zhiwuvsani;


import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import com.zzz.game.layer.WelComeLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {
	private CCDirector director;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
		setContentView(surfaceView);
		director = CCDirector.sharedDirector();
		director.attachInView(surfaceView);//���߳�
		director.setScreenSize(480, 320);
		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);//����
		director.setDisplayFPS(true);//����ʾ֡��
		CCScene scene = CCScene.node();
		scene.addChild(new WelComeLayer());
		//���ݹ�����
		director.runWithScene(scene);
	}

	@Override
	protected void onResume() {
		director.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		director.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		director.end();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Process.killProcess(Process.myPid());
		return super.onKeyDown(keyCode, event);
	}
}
