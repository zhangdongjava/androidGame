package com.zzz.game.layer;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;
import com.zzz.game.base.BaseLayer;
import com.zzz.game.tool.CommonUtil;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.*;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;

import java.util.ArrayList;

public class WelComeLayer extends BaseLayer {
	private static final int TAG_START = 0;
	
	private CCSprite bar= CCSprite.sprite("image/loading/loading_01.png");
	private volatile CCSprite startGame =  CCSprite.sprite("image/loading/loading_start.png");
	private boolean isClick = false;
	public WelComeLayer(){
		consumeTime();
		init();
	}

	/**
	 * ��ʱ��������Ҫ�������汾��Ϣ
	 */
	private void consumeTime() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// ��ʱ�������������磬�汾��⣬Ԥ����ͼƬ��Ԥ���������ļ�
				
				SystemClock.sleep(4000);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				setIsTouchEnabled(true);
				bar.removeSelf();

				startGame.setVisible(true);
				super.onPostExecute(result);
			}

		}.execute();

	}

	private void init() {
		// TODO Auto-generated method stub
		loadBGM();
		splash();
	}


	private void splash() {
		// TODO Auto-generated method stub
		CCSprite loading = CCSprite.sprite("image/popcap_logo.png");
		loading.setPosition(cgSize.width/2,cgSize.height/2);//������ʾ
		this.addChild(loading);
		//��ʾ1s��0.5s��ɾ��
		CCSequence ccSequence = CCSequence.actions( CCDelayTime.action(1),CCHide.action(),CCDelayTime.action(0.5F),CCCallFunc.action(this, "login"));
		loading.runAction(ccSequence);
	}
	//ע��һ��ҪΪpublic,����ɿ�CCCallFuncԴ��

	public void login(){
		CCSprite bg = CCSprite.sprite("image/background.png");
		bg.setAnchorPoint(0, 0);
		this.addChild(bg);
		//���ñ���
		CCLabel cLabel = CCLabel.makeLabel("ֲ���ս��ʬ", "Roboto_Bold.ttf", 35);//�������壬�м����Ϊttf�ļ���20Ϊ�����С
		cLabel.setColor(ccColor3B.ccc3(255,255, 255));//��ʼֵ
		cLabel.setPosition(cgSize.width/2,220);
		this.addChild(cLabel);
		setName("������", 170f);
		setName("�µǿ�", 130f);
		//���ý�����
		bar  = CCSprite.sprite("image/loading/loading_01.png");
		bar.setPosition(cgSize.width/2, 25);
		this.addChild(bar);
		//����������֡
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		String fileName = "image/loading/loading_%02d.png";//��ռλ����ʾͼƬβ������ʽΪ��λ������01��11��
		for (int num = 1; num <= 9; num++) {
			frames.add(CCSprite.sprite(String.format(fileName, num))
					.displayedFrame());
		}

		CCAnimation animation = CCAnimation.animation("", 0.2f, frames);
		CCAnimate animate = CCAnimate.action(animation, false);// ������������ֹͣ������֡���ţ�true�������ֻ�벥��һ�Σ�false��
		bar.runAction(animate);
		
		startGame = CCSprite.sprite("image/loading/loading_start.png");
		startGame.setPosition(cgSize.width / 2, 25);
		startGame.setVisible(false);
		this.addChild(startGame, 0, TAG_START);
		
	
	}
	
	//��������
	public void setName(String name,float y){
			//�ı���ɫ����
			ccColor3B c = ccColor3B.ccc3(0,-100,0);
			CCTintBy tintBy = CCTintBy.action(1, c);
			CCLabel cLabel = CCLabel.makeLabel(name, "Roboto_Thin.ttf", 20);//�������壬�м����Ϊttf�ļ���20Ϊ�����С
			cLabel.setColor(ccColor3B.ccc3(255,228, 0));//��ʼֵ
			cLabel.setPosition(cgSize.width/2,y);
			
			CCSequence ccSequence = CCSequence.actions(tintBy, tintBy.reverse());
			CCRepeatForever ccRepeatForever = CCRepeatForever.action(ccSequence);
			this.addChild(cLabel,1);
			cLabel.runAction(ccRepeatForever);
		
	}
	/**
	 * ���ر�������
	 */
	private void loadBGM() {
		engine.playSound(getContext(), com.zzz.game.R.raw.start, true);
	}
//���ð�ť�����ת����Ϸҳ
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if(CommonUtil.isClicke(event, this, this.getChildByTag(TAG_START))){
			engine.playEffect(getContext(), com.zzz.game.R.raw.onclick);
			CCScene scene = CCScene.node();
			scene.addChild(new FightLayer());
			CCFadeTransition transition = CCFadeTransition.transition(0.5F, scene);
			
			//�滻����
			CCDirector.sharedDirector().replaceScene(transition);
			
		}
		return super.ccTouchesEnded(event);
	}
	
	
}
