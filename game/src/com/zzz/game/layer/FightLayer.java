package com.zzz.game.layer;

import android.view.MotionEvent;
import com.zzz.game.base.BaseLayer;
import com.zzz.game.control.GameController;
import com.zzz.game.sql.ShowPlant;
import com.zzz.game.tool.CommonUtil;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.List;

public class FightLayer extends BaseLayer {
	public static final int TAG_CHOSE = 10;
	private CCTMXTiledMap map;
	private CCSprite ready;
	private List<ShowPlant> chosePlantList;
	private CCSprite choseContainer;
	public FightLayer() {
		// TODO Auto-generated constructor stub
		init();
		/*CCSprite sprite = CCSprite.sprite("image/welcome.jpg");
		this.addChild(sprite);*/
	}

	private void init() {
		// TODO Auto-generated method stub
		engine.playSound(getContext(), com.zzz.game.R.raw.day, true);
		loadMap();
		loadContain();
		ready();
	}
	private ShowPlant getSprite(int num){
		ShowPlant plant1 = new ShowPlant(num);
		CGPoint pos = CGPoint.ccp(
				(75 + chosePlantList.size() * 53)*0.65f,
				cgSize.height - 65*0.65F);
		// moveto����
		CCMoveTo moveTo = CCMoveTo.action(0.3f, pos);
		plant1.getDefaultImg().runAction(moveTo);
		return plant1;
	}
	private void loadContain() {
		// TODO Auto-generated method stub
		chosePlantList = new ArrayList<ShowPlant>();
		//Ĭ��ֻ�������ֲ��
		
		chosePlantList.add(getSprite(1));
		chosePlantList.add(getSprite(4));
		
		choseContainer = CCSprite.sprite("image/fight/chose/fight_chose.png");
		choseContainer.setAnchorPoint(0, 1);
		choseContainer.setPosition(0, cgSize.height);
		this.addChild(choseContainer,0,TAG_CHOSE);
		for (ShowPlant item : chosePlantList) {
			CCSprite plant = item.getDefaultImg();
			
			plant.setScale(0.65F);
			// plant.setAnchorPoint(0, 0.5f);
			this.addChild(plant,1);
		}

		// �������ֲ�������������ţ����������Ѿ�ѡ���ֲ����Ϣ��
		choseContainer.setScale(0.65f);
	}

	private void ready(){
		//���ص�һ��ͼƬ
		ready = CCSprite.sprite("image/fight/startready_01.png");
		ready.setAnchorPoint(0.5f,0.5f);
		ready.setPosition(cgSize.width/2,cgSize.height/2);
		//���ſ�ʼ����֡
		CCSequence ccSequence = CCSequence.actions(CommonUtil.getAnimation(null, 3, "image/fight/startready_%02d.png"),CCCallFunc.action(this, "go"));
		ready.runAction(ccSequence);
		this.addChild(ready);
	}
	//���ص�ͼ
	private void loadMap() {
		// TODO Auto-generated method stub
		map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
		map.setAnchorPoint(0.5f,0.5f);
		CGSize contentSize = map.getContentSize();
		//��������һ��
		map.setPosition(contentSize.width/2,contentSize.height/2);
		this.addChild(map);
	}
	//��ʼ��Ϸ
	public void go(){
		ready.removeSelf();
		this.setIsTouchEnabled(true);
		GameController.getInstance().start(map, chosePlantList);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		if(GameController.getInstance().isStart()){
			GameController.getInstance().handlerTouch(event);
		}
		return super.ccTouchesBegan(event);
	}
	
}
